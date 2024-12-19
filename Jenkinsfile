pipeline{
    agent any
    tools{
        maven 'maven-3.9.8'
    }
    environment {
        SONAR_SCANNER_HOME = tool name: 'sonarqube'
    }
    stages{
        stage('Build and Package'){
            steps{
                sh 'mvn clean package -DskipTests'
            }
        }
        stage ("SAST - SonarQube") {
                    // currently skip test cases
                        steps {
                            dir('/var/lib/jenkins/workspace/fusionIQ/Fusion-Backend'){
                                 script {
                                    sh '''
                                        mvn clean verify sonar:sonar -DskipTests\
                                        -Dsonar.projectKey=fusion-be \
                                        -Dsonar.host.url=http://3.85.120.238:9000 \
                                        -Dsonar.login=sqp_fa932886fca17cdbb24fe701faba574c192b1bba
                                    '''
                        }
                    }
                }
        }
        stage('push to s3') {
            steps{
                sh 'aws s3 cp /var/lib/jenkins/workspace/fusionIQ/Fusion-Backend/target/fusionIq-0.0.1-SNAPSHOT.jar s3://fusioniq-v3-be/'
            }
        }
    }
}