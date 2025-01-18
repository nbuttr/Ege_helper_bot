# Запуск бота: <br>
**1.** Установить **docker desktop** на компьютер.<br>
**2.** Скачаать проект, проставить нужные проперти в application.properties и перейти в директорию с ним через командную строку.<br>
**3.** Прописать команду *docker-compose up*.<br>
**4.** Командой *docker cp <путь к файлу>/dum1p.sql use-bot-postgresql:/dump.sql* скопировать в контейнер дамп базы данных.<br> 
**5.** Зайти в контейнер **use-bot-postgresql** через **docker desktop** и выполнить следующуие команды: *psql -U bot-user -d use-bot-db*, *DROP SCHEMA PUBLIC CASCADE;*, *CREATE SCHEMA PUBLIC;*, нажать ctrl+z.<br>
**6.** Выполнить следующую команду: *psql -U bot-user -d use-bot-db -f /dump.sql*.<br>
**7.** Перезапустить контейнер с ботом.<br>

