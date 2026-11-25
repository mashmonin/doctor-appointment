pipeline {
    agent {
        node { label 'dind' }
    }
    tools {
        // ЗАМЕЧАНИЕ: oc 4.7.12 — устаревшая версия (текущая 4.14+). Убедитесь, что версия соответствует целевому кластеру OpenShift.
        oc 'oc-4.7.12'
    }
    environment {
        // ЗАМЕЧАНИЕ: credentials('') — пустой ID. Необходимо указать реальный ID из Jenkins Credentials Store, иначе пайплайн упадёт при старте.
        TOKEN_OPENSHIFT = credentials('')
        // ЗАМЕЧАНИЕ: REGISTRY, OS_API_URL, OS_PROJECT — пустые строки. Все три переменные используются в stage'ах ниже и должны быть заполнены.
        REGISTRY = ''
        OS_API_URL = ''
        OS_PROJECT = ''
    }
    stages {
        stage('Maven test & build') {
            when {
                anyOf {
                    environment name: 'GIT_BRANCH', value: 'develop'
                    expression {env.GIT_BRANCH==~/PR-.*/}
                }
            }
            environment {
                // ЗАМЕЧАНИЕ: MAVEN_OPTS пустая — переменная не несёт смысла до заполнения (например, '-Xmx512m').
                MAVEN_OPTS = ''
            }
            steps {
                script {
                    withCredentials([
                    // ЗАМЕЧАНИЕ: credentialsId: '' и variable: '' — оба пустые. Пайплайн упадёт при попытке резолвить credentials.
                    string(credentialsId: '', variable: ''),
                    usernamePassword(credentialsId: '',
                    usernameVariable: '',
                    passwordVariable: '')])
                    {
                        withMaven(jdk: 'openjdk-17', maven: 'Maven 3.8'){
                            sh 'mvn -gs assets/settings.xml clean install'
                        }
                    }
                }
            }
        }
        stage('Docker build') {
            when {
                anyOf {
                    environment name: 'GIT_BRANCH', value: 'develop'
                    expression {env.GIT_BRANCH==~/PR-.*/}
                }
            }
            steps {
                script {
                    withCredentials([usernamePassword(
                    // ЗАМЕЧАНИЕ: credentialsId: '', usernameVariable: '', passwordVariable: '' — всё пустое, credentials не будут загружены.
                    credentialsId: '',
                    usernameVariable: '',
                    passwordVariable: '')]){
                    pom = readMavenPom file: 'pom.xml'
                    env.APP_BUILD = String.format(pom.getVersion()).trim()
                    sh 'echo ${APP_BUILD}'
                    // ЗАМЕЧАНИЕ: '${} ' — невалидный bash-синтаксис, внутри ${ } должно быть имя переменной. Также URL реестра после 'https://' пустой.
                    sh 'docker login -u ${} -p ${} https://'
                    sh 'docker build -f openshift/Dockerfile --build-arg APP_BUILD=${APP_BUILD} -t ${REGISTRY}/doctor-appointment:${BUILD_NUMBER} -t ${REGISTRY}/doctor-appointment:latest .'
                    // ЗАМЕЧАНИЕ: URL реестра после 'https://' пустой.
                    sh 'docker logout https://'
                    }
                }
            }
        }
        stage('Test OC configs') {
            when {
                anyOf {
                    environment name: 'GIT_BRANCH', value: 'develop'
                    expression {env.GIT_BRANCH==~/PR-.*/}
                }
            }
            steps {
                script {
                    sh 'oc login --token=${TOKEN_OPENSHIFT} --insecure-skip-tls-verify --server=${OS_API_URL} && \
                        oc project ${OS_PROJECT} && oc status'
                    sh 'oc apply -f openshift/kinds -R --dry-run'
                }
            }
        }
        stage('Docker push') {
            when {
                anyOf {
                    environment name: 'GIT_BRANCH', value: 'develop'
                }
            }
            steps {
                script {
                    withCredentials([usernamePassword(
                    // ЗАМЕЧАНИЕ: credentialsId: '', usernameVariable: '', passwordVariable: '' — пустые. ${SERVICE_USERNAME} и ${SERVICE_PASSWORD} ниже не будут резолвиться.
                    credentialsId: '',
                    usernameVariable: '',
                    passwordVariable: '')]){
                    // ЗАМЕЧАНИЕ: URL реестра после 'https://' пустой.
                    sh 'docker login -u ${SERVICE_USERNAME} -p ${SERVICE_PASSWORD} https://'
                    sh 'docker push ${REGISTRY}/doctor-appointment:${BUILD_NUMBER}'
                    sh 'docker push ${REGISTRY}/doctor-appointment:latest'
                    // ЗАМЕЧАНИЕ: IMAGE_SHA задаётся как shell-переменная внутри sh-шага и не будет доступна в следующих stage'ах. Нужно env.IMAGE_SHA = ...
                    sh 'docker inspect --format="{{index .RepoDigests 0}}" ${REGISTRY}/doctor-appointment:${BUILD_NUMBER} | sed "s/.*@//" > sha && \
                    IMAGE_SHA=`cat sha` && rm -rf sha && \
                    sed -i "s/IMAGE_SHA/$IMAGE_SHA/g" openshift/kinds/doctor-appointment.yaml && \
                    echo IMAGE_SHA is: ${IMAGE_SHA}'
                    // ЗАМЕЧАНИЕ: URL реестра после 'https://' пустой.
                    sh 'docker logout https://'
                    }
                }
            }
        }
        stage('Maven deploy') {
            when {
                anyOf {
                    environment name: 'GIT_BRANCH', value: 'develop'
                }
            }
            steps {
                script {
                    withCredentials([
                    string(credentialsId: '', variable: ''),
                    // ЗАМЕЧАНИЕ: структура withCredentials сломана — usernameVariable и passwordVariable оказались снаружи usernamePassword(), это синтаксическая ошибка Groovy.
                    usernamePassword(credentialsId: ''),
                    usernameVariable: '',
                    passwordVariable: '')])
                    {
                        withMaven(jdk: 'openjdk-17', maven: 'Maven 3.8'){
                            // ЗАМЕЧАНИЕ: -Durl и -DaltDeploymentRepository содержат пустые URL ('https://').
                            sh 'mvn -gs assets/settings.xml deploy \
                            -Durl=https:// \
                            -DpomFile=pom.xml \
                            -DaltDeploymentRepository=maven-lib-dev::default::https:// \
                            -DrepositoryId=maven-lib-dev'
                        }
                    }
                }
            }
        }
        stage('Deploy dev') {
            when {
                anyOf {
                    environment name: 'GIT_BRANCH', value: 'develop'
                }
            }
            steps {
                script {
                    // ЗАМЕЧАНИЕ: '--tokens=' (с 's') — неверный флаг, правильно '--token='.
                    sh 'oc login --token=${TOKEN_OPENSHIFT} --insecure-skip-tls-verify --server=${OS_API_URL} && \
                    oc project ${OS_PROJECT} && oc status'
                    // ЗАМЕЧАНИЕ: ${IMAGE_SHA} здесь будет пустой — переменная не была передана через env в stage 'Docker push'.
                    sh 'echo IMAGE_SHA is: ${IMAGE_SHA}'
                    // ЗАМЕЧАНИЕ: sed по doctor-appointment.yaml применяется второй раз (первый — в stage 'Docker push').
                    // Если IMAGE_SHA уже подставлен, повторный sed не найдёт плейсхолдер и молча завершится без замены.
                    sh 'sed -i "s/IMAGE_SHA/$IMAGE_SHA/g" openshift/kinds/doctor-appointment.yaml'
                    sh 'oc apply -f openshift/kinds -R'
                }
            }
        }
    }
}