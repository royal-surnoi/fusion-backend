pipeline{
    agent any
    tools{
        maven 'maven-3.9.8'
    }
    stages{
        stage('Build and Package'){
            steps{
                sh 'mvn clean install -DskipTests'
            }
        }
    }
}