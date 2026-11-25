workspace "Lavamed" "Информационная система клиники Lavamed" {
    // Включение иерархических идентификаторов вида systemName.containerName - поможет избежать конфликтов наименования при масштабировании
    !identifiers hierarchical

    model {
    		properties {
    			"structurizr.groupSeparator" "/"
    		}

    		// Общие элементы системы
    		!include "common.dsl"

    		// Внешний почтовый клиент
    		!include "domains/email-client/model.dsl"

    		// Сервис записи к врачу
    		!include "domains/doctor-appointment/model.dsl"

    }

    views {

       !include "domains/doctor-appointment/view.dsl"
       !include "domains/email-client/view.dsl"

            styles {

                element "Person" {
                    shape Person
                    background #1168bd
                    color #ffffff
                }

                element "Software System" {
                    background #1168bd
                    color #ffffff
                }

                element "Frontend" {
                    shape WebBrowser
                    background #438dd5
                    color #ffffff
                }

                element "Backend" {
                    shape RoundedBox
                    background #438dd5
                    color #ffffff
                }

                element "Database" {
                    shape Cylinder
                    background #438dd5
                    color #ffffff
                }

                element "Infrastructure" {
                    shape RoundedBox
                    background #85bbf0
                    color #000000
                }

    			// Новый элемент — зелёная подсветка
    			element "New" {
    				border solid
    				stroke #2EA44F
    				strokeWidth 5
    			}

    			// Изменяемый элемент — жёлтая подсветка
    			element "Changed" {
    				border solid
    				stroke #F39C12
    				strokeWidth 5
    			}

    		    // Согласованный элемент — синяя подсветка
    		    element "Approved" {
    		        border solid
    		        stroke #2980B9
    		        strokeWidth 5
    		    }

    			// Новые связи — зелёная линия
    			relationship "New" {
    				color #2EA44F
    				thickness 3
    			}

    			// Изменяемые связи — жёлтая линия
    			relationship "Changed" {
    				color #F39C12
    				thickness 3

                }

    			// Изменяемые связи — синяя линия
    			relationship "Approved" {
    				color #2980B9
    				thickness 3

                }

            }

    }

}
