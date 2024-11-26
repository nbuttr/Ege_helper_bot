package com.study.bot.bot;

import com.study.bot.dto.ImageToParagraphDto.ImageToParagraphDto;
import com.study.bot.dto.paragraph.CreateParagraphDto;
import com.study.bot.dto.paragraph.ParagraphDto;
import com.study.bot.dto.user.CreateUserDto;
import com.study.bot.dto.user.UserDto;
import com.study.bot.entity.type.ImageTypes;
import com.study.bot.service.ParagraphService;
import com.study.bot.service.S3Service;
import com.study.bot.state.AdminState;
import com.study.bot.state.UserRegState;
import com.study.bot.state.UserState;
import com.study.bot.state.stage.AdminStage;
import com.study.bot.state.stage.UserRegStage;
import com.study.bot.entity.type.UserRegistrationStatus;
import com.study.bot.entity.type.UserRoles;
import com.study.bot.service.UserService;
import com.study.bot.state.stage.UserStage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class StudyBot extends TelegramLongPollingBot {

    @Autowired
    private UserService userService;

    @Autowired
    private ParagraphService paragraphService;

    @Autowired
    S3Service s3;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${app.work-dir}")
    private String workDirectory;



    private final Map<Long, UserRegState> userRegStates = new HashMap<>();
    private final Map<Long, UserState> userStates = new HashMap<>();
    private final Map<Long, AdminState> adminStates = new HashMap<>();
    private final List<ImageToParagraphDto> imageToParagraphDtos = new ArrayList<>();

    public StudyBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    private void sendInitialMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Что сделать:");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Регистрация в боте");
        keyboardMarkup.setKeyboard(List.of(row1));
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.getCallbackQuery() != null){
            handleCallback(update);
        }else if(update.hasMessage() && update.getMessage().hasText() || update.getMessage().hasPhoto()) {
            UserDto userDto = userService.findByChatId(update.getMessage().getChatId());
            if(userDto == null || userDto.getRegistrationStatus() == UserRegistrationStatus.NOT_FINISHED) {
                handleIncomingMessage(update.getMessage());
            } else if (userDto.getUserRole() == UserRoles.ADMIN || update.getMessage().hasPhoto()) {
                adminPanel(update);
            } else if (userDto.getUserRole() == UserRoles.USER) {
                userPanel(update);
            }
        }

    }

    private void handleIncomingMessage(Message message) {
        long chatId = message.getChatId();
        String text = message.getText();

        UserRegState userRegState = userRegStates.getOrDefault(chatId, new UserRegState());

        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        switch (userRegState.getStage()) {
            case START -> {
                response.setText("Добро пожаловать! Пожалуйста, введите ваше имя:");
                userRegState.setStage(UserRegStage.ASK_NAME);
            }
            case ASK_NAME -> {
                userRegState.setName(text);
                response.setText("Введите вашу фамилию:");
                userRegState.setStage(UserRegStage.ASK_SURNAME);
            }
            case ASK_SURNAME -> {
                userRegState.setSurname(text);
                response.setText("Вы хотите зарегистрироваться как пользователь или админ? (Введите 'юзер' или 'админ')");
                userRegState.setStage(UserRegStage.CHOOSE_ROLE);
            }
            case CHOOSE_ROLE -> {
                if (text.equalsIgnoreCase("админ")) {
                    response.setText("Введите пароль администратора:");
                    userRegState.setStage(UserRegStage.ASK_ADMIN_PASSWORD);
                    userRegState.setRole(UserRoles.ADMIN);
                } else if (text.equalsIgnoreCase("юзер")) {
                    response.setText("Регистрация завершена. Добро пожаловать в пользовательскую панель!");
                    userRegState.setRole(UserRoles.USER);
                    userRegState.setStage(UserRegStage.COMPLETE);
                    UserRegState userRegState1 = userRegStates.get(chatId);
                    userService.create(new CreateUserDto(chatId, userRegState1.getRole(), userRegState1.getName(), userRegState1.getSurname(), UserRegistrationStatus.COMPLETED));
                } else {
                    response.setText("Пожалуйста, выберите корректную роль: 'юзер' или 'админ'");
                }
            }
            case ASK_ADMIN_PASSWORD -> {
                if (text.equals(adminPassword)) {
                    UserRegState userRegState1 = userRegStates.get(chatId);
                    userService.create(new CreateUserDto(chatId, userRegState1.getRole(), userRegState1.getName(), userRegState1.getSurname(), UserRegistrationStatus.COMPLETED));
                    response.setText("Пароль верный. Добро пожаловать в админ-панель!");
                    userRegState.setStage(UserRegStage.COMPLETE);
                } else {
                    response.setText("Неверный пароль. Попробуйте снова:");
                }
            }
            case COMPLETE -> {
                if (userRegState.getRole().equals(UserRoles.ADMIN)) {
                    response.setText("Вы уже зарегистрированы как админ.");
                } else {
                    response.setText("Вы уже зарегистрированы как пользователь.");
                }
            }
        }

        userRegStates.put(chatId, userRegState);

        sendResponse(response);
    }

    private void userPanel(Update update) {
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();


        UserState userState = userStates.getOrDefault(chatId, new UserState());
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        switch (userState.getStage()) {
            case NONE -> {
                if (text.equals("Получить список параграфов")) {
                    userState.setStage(UserStage.CONFIRM_ACTION);
                    response.setText("Вы хотите получить список параграфов? Введите 'да' для подтверждения.");
                } else {
                    response.setText("Неизвестная команда. Введите 'Получить список параграфов'.");
                }
            }
            case CONFIRM_ACTION -> {
                if (text.equalsIgnoreCase("да")) {
                    userState.setStage(UserStage.GET_PARAGRAPH);
                    response.setText("Список параграфов:\n");
                    sendParagraphList(chatId);
                } else {
                    userState.setStage(UserStage.NONE);
                    response.setText("Действие отменено.");
                }
            }
            case GET_PARAGRAPH -> {
                UUID paragraphId = UUID.fromString(text); // Поиск параграфа по имени
                if (paragraphId != null) {
                    userState.setStage(UserStage.SELECT_PARAGRAPH);
                    handleCallback(update);
                    userState.setCurrentParagraphId(paragraphId);
                    response.setText("Вы выбрали параграф. Хотите посмотреть изображения?");
                } else {
                    response.setText("Параграф не найден. Попробуйте снова.");
                }
            }
            case SELECT_PARAGRAPH -> {
                if (text.equalsIgnoreCase("да")) {
                    userState.setStage(UserStage.VIEW_IMAGES);
                    response.setText("Отправляю изображения...");
                    handleCallback(update);
                } else {
                    userState.setStage(UserStage.NONE);
                    response.setText("Действие отменено.");
                }
            }
            case VIEW_IMAGES -> {
                response.setText("Вы уже просматриваете изображения.");
            }
            default -> {
                response.setText("Неизвестное состояние.");
                userState.setStage(UserStage.NONE);
            }
        }

            userStates.put(chatId, userState);
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

    }


    private void adminPanel(Update update) {
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();


        AdminState adminState = adminStates.getOrDefault(chatId, new AdminState());
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        switch (adminState.getStage()) {
            case NONE -> {
                if (adminState.getCurrentParagraphId() != null){
                    ParagraphDto paragraphDto = paragraphService.getById(adminState.getCurrentParagraphId());
                    paragraphDto.setImageToParagraphs(imageToParagraphDtos);
                    paragraphService.update(adminState.getCurrentParagraphId(), paragraphDto);
                    imageToParagraphDtos.clear();
                }else{
                    imageToParagraphDtos.clear();
                }
                if(text.equals("Создать параграф")){
                    adminState.setStage(AdminStage.CREATE_PARAGRAPH);
                    response.setText("Введите название параграфа:");
                }else if(text.equals("Добавить изображения в параграф")){
                    adminState.setStage(AdminStage.ADD_IMAGE_TO_PARAGRAPH);
                    response.setText("Для подтверждения действия введите: 'да' ");
                }else {
                    adminState.setStage(AdminStage.NONE);
                    response.setText("Выберите действие: \n1. Создать параграф \n2. Добавить изображения в параграф");
                }
            }
            case CREATE_PARAGRAPH -> {
                adminState.setCurrentParagraphName(text);
                CreateParagraphDto createParagraphDto = new CreateParagraphDto(text);
                ParagraphDto paragraphDto = paragraphService.create(createParagraphDto);
                adminState.setCurrentParagraphId(paragraphDto.getId());
                response.setText("Параграф \"" + text + "\" создан. Теперь добавьте изображение.");
                adminState.setStage(AdminStage.ASK_IMAGE_URL);
            }
            case ASK_IMAGE_URL -> {
                if(update.getMessage().hasText()){
                    adminState.setStage(AdminStage.NONE);
                    response.setText("Подтвердите действие, введите 'да' ");
                }else if(update.getMessage().hasPhoto()){
                    String url = getPhoto(update);
                    if(url == null){
                        response.setText("Ошибка загрузки фото.");
                        adminState.setStage(AdminStage.ASK_IMAGE_URL);
                    }else {
                        adminState.setUrl(url);
                        response.setText("Введите порядковый номер для изображения:");
                        adminState.setStage(AdminStage.ASK_IMAGE_ORDINAL);
                    }
                }
            }
            case ASK_IMAGE_ORDINAL -> {
                try {
                    int ordinalNumber = Integer.parseInt(text);
                    adminState.setCurrentOrdinalNumber(ordinalNumber);
                    imageToParagraphDtos.add(new ImageToParagraphDto(UUID.randomUUID(),
                            paragraphService.getById(adminState.getCurrentParagraphId()),
                            adminState.getUrl(),  ImageTypes.THEORY, ordinalNumber
                            ));
                    response.setText("Изображение добавлено! Добавьте следующее изображение или напишите \"стоп\", чтобы завершить добавление.");
                    adminState.setStage(AdminStage.ASK_IMAGE_URL);
                } catch (NumberFormatException e) {
                    response.setText("Пожалуйста, введите корректный порядковый номер (целое число).");
                }
            }
            case ADD_IMAGE_TO_PARAGRAPH -> {
                List<ParagraphDto> paragraphDtos = paragraphService.findAll();
                if(paragraphDtos.isEmpty()){
                    response.setText("Список параграфов пуст. Сначала создайте параграф");
                    adminState.setStage(AdminStage.NONE);
                }else{
                    StringBuilder paragraphList = new StringBuilder("Список параграфов: \n");
                    for(ParagraphDto paragraphDto : paragraphDtos){
                        paragraphList.append("- ").append(paragraphDto.getParagraphName()).append("\n");
                    }
                    response.setText(paragraphList + "\nВведите название параграфа из списка: ");
                    adminState.setStage(AdminStage.GET_ALL_PARAGRAPHS);
                }
            }
            case GET_ALL_PARAGRAPHS -> {
                String paragraphName = update.getMessage().getText();
                Optional<ParagraphDto> selectedParagraph = Optional.ofNullable(paragraphService.findByParagraphName(paragraphName));

                if (selectedParagraph.isPresent()) {
                    adminState.setCurrentParagraphId(selectedParagraph.get().getId());
                    response.setText("Вы выбрали параграф: " + paragraphName + ". Теперь отправьте изображение для добавления.");
                    adminState.setStage(AdminStage.ASK_IMAGE_URL);
                } else {
                    response.setText("Параграф с таким названием не найден. Попробуйте снова.");
                    adminState.setStage(AdminStage.ADD_IMAGE_TO_PARAGRAPH);
                }
            }
            default -> response.setText("Выберите действие.");
        }

        adminStates.put(chatId, adminState);
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getPhoto(Update update){
        List<PhotoSize> photos = update.getMessage().getPhoto();
        String photoId = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).map(PhotoSize::getFileId).orElse("");
        log.info("PhotoId: {}", photoId);
        if (!photoId.isEmpty()) {
            try {
                if (Files.notExists(Path.of(workDirectory))) {
                    Files.createDirectories(Path.of(workDirectory));
                }
                File file = execute(new GetFile(photoId));

                String fileUrl = getFileUrl(file.getFilePath());

                downloadFile(fileUrl,workDirectory);

            } catch (TelegramApiException | IOException e) {
                log.info(e.getMessage());
            }
        }
        java.io.File file = new java.io.File(workDirectory + "/photo.jpg");
        return s3.uploadFileToS3(file.getAbsolutePath());
    }

    public void sendParagraphList(Long chatId) {
        List<ParagraphDto> paragraphs = paragraphService.findAll();

        if (paragraphs.isEmpty()) {
            sendMessage(chatId, "Список параграфов пуст. Сначала создайте параграф.");
            return;
        }

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (ParagraphDto paragraph : paragraphs) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(paragraph.getParagraphName());
            button.setCallbackData("paragraph_" + paragraph.getId());
            rows.add(List.of(button));
        }

        keyboard.setKeyboard(rows);
        sendMessageWithKeyBoard(chatId, "Выберите параграф:", keyboard);
    }

    public void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();

        if (callbackData.startsWith("paragraph_")) {
            String paragraphId = callbackData.split("_")[1];

            sendImagesForParagraph(chatId, UUID.fromString(paragraphId));
        }
    }

    public void sendImagesForParagraph(Long chatId, UUID paragraphId) {
        List<ImageToParagraphDto> images = paragraphService.getImageToParagraphDtos(paragraphId);

        if (images.isEmpty()) {
            sendMessage(chatId, "В данном параграфе нет изображений.");
            return;
        }

        images.sort(Comparator.comparingInt(ImageToParagraphDto::getOrdinalNumber));

        for (ImageToParagraphDto image : images) {
            sendPhoto(chatId, image.getUrl());
        }
    }

    private void sendPhoto(Long chatId, String url) {
        SendPhoto photo = new SendPhoto();
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(url));

        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageWithKeyBoard(Long chatId, String mes, InlineKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(mes);
        message.setReplyMarkup(keyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(Long chatId, String text){
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try{
            execute(sendMessage);
        }catch(TelegramApiException e){
            log.error("Ошибка отправки сообщения", e);
        }
    }

    private void sendResponse(SendMessage response) {
        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getFileUrl(String filePath) {
        return "https://api.telegram.org/file/bot" + getBotToken() + "/" + filePath;
    }

    private void downloadFile(String url, String destination) throws IOException {
        try (InputStream in = new URL(url).openStream()) {
            if (Files.notExists(Path.of(workDirectory))) {
                Files.createDirectories(Path.of(workDirectory));
            }
            Path destinationFile = Path.of(workDirectory).resolve("photo.jpg");
            Files.copy(in, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
           log.info("Download Error Message: “File not found. Check the file url and try again.");
        }
    }

    @Override
    public String getBotUsername() {
        return "Use_Helper";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
