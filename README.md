#Запуск бота: 
**1.** Установить docker desktop на компьютер. 
**2.** Скачаать проект и перейти в директорию с ним через командную строку. 
**3.** Прописать команду *docker-compose up*.
**4.** Командой *docker cp <путь к файоу>/dum1p.sql use-bot-postgresql:/dump.sql* скопировать в контейнер дамп базы данных. 
**5.** Зайти в контейнер use-bot-postgresql через docker desktop и выполнить следующуие команды: *psql -U bot-user -d use-bot-db*, *DROP SCHEMA PUBLIC CASCADE;*, *CREATE SCHEMA PUBLIC*, нажать ctrl+z.
**6.** Выполнить следующую команду: *psql -U bot-user -d use-bot-db -f /dump.sql*.
**7.** Перезапустить контейнер с ботом.

