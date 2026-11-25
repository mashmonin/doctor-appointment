

    patient = person "Пациент" "Пользователь системы, записывающийся к врачу"

    doctorAppointment = softwareSystem "DoctorAppointment" "Система онлайн-записи к врачу" {
        tags "Approved"

        nginx = container "Nginx" "Реверс-прокси: SSL-терминация и раздача статических файлов" "Nginx" {
            tags "Infrastructure" "Approved"
        }

        frontend = container "Frontend" "Клиентское SPA-приложение для записи к врачу и просмотра списка записей" "Vite + React" {
            tags "Frontend" "Approved"
        }

        backend = container "Backend" "Сервис бизнес-логики: управление записями и расписанием врачей" "Java Spring Boot + Gradle" {
            tags "Backend" "Approved"
        }

        database = container "Database" "Хранилище данных о пациентах, врачах и записях" "PostgreSQL" {
            tags "Database" "Approved"
        }

        kafka = container "Apache Kafka" "Брокер сообщений для асинхронного обмена событиями между сервисами" "Apache Kafka" {
            tags "Infrastructure" "Approved"
        }

        notificationService = container "Сервис уведомлений" "Backend-сервис: получает события из топика 1 и отправляет email-уведомления пациентам через внешний почтовый клиент" "Java Spring Boot" {
            tags "Backend" "Approved"
        }

    }

    patient -> doctorAppointment.nginx "Открывает приложение, записывается к врачу, просматривает список записей" "HTTPS" {
        tags "Approved"
    }
    doctorAppointment.nginx -> doctorAppointment.frontend "Раздаёт статические файлы" "HTTP" {
        tags "Approved"
    }
    doctorAppointment.nginx -> doctorAppointment.backend "Проксирует API-запросы" "HTTP" {
        tags "Approved"
    }
    doctorAppointment.frontend -> doctorAppointment.nginx "Отправляет API-запросы" "HTTPS" {
        tags "Approved"
    }
    doctorAppointment.backend -> doctorAppointment.database "Читает и записывает данные о записях, расписании и статусах отправки уведомлений" "JDBC" {
        tags "Approved"
    }
    doctorAppointment.backend -> doctorAppointment.kafka "Публикует событие о создании записи в топик 1" "Kafka" {
        tags "Approved"
    }
    doctorAppointment.notificationService -> doctorAppointment.kafka "Подписывается на топик 1, получает события о создании записей" "Kafka" {
        tags "Approved"
    }
    doctorAppointment.notificationService -> emailClient "Отправляет email-напоминание пациенту" "SMTP" {
        tags "Approved"
    }
    doctorAppointment.notificationService -> doctorAppointment.kafka "Публикует статус отправки уведомления в топик 2" "Kafka" {
        tags "Approved"
    }
    doctorAppointment.backend -> doctorAppointment.kafka "Читает статус отправки уведомления из топика 2" "Kafka" {
        tags "Approved"
    }


