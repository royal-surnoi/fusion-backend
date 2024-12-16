pipeline{
    agent any
    tools{
        maven 'maven-3.9.8'
    }
    environment {
        // docker_registry = 'iamroyalreddy/fusion-be'
        // DOCKERHUB_CREDENTIALS = credentials('docker-credentials')
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
                                        mvn clean verify sonar:sonar \
                                            -Dsonar.projectKey=fusion-be \
                                            -Dsonar.host.url=http://54.90.107.237:9000 \
                                            -Dsonar.login=sqp_23cf9273aef4580e8aad217624d1773459c144d9
                                    '''
                                 }
                            // script {
                            //     withSonarQubeEnv('sonarqube') {
                            //         withCredentials([string(credentialsId: 'sonar-fe-credentials', variable: 'SONAR_TOKEN')]){
                            //             withEnv(["PATH+SONAR=$SONAR_SCANNER_HOME/bin"]) {
                            //                 sh '''
                            //                     mvn clean verify sonar:sonar -DskipTests \
                            //                         -Dsonar.projectKey=fusion-be \
                            //                         -Dsonar.projectName='fusion-be' \
                            //                         -Dsonar.host.url=$SONAR_HOST_URL \
                            //                         -Dsonar.token=$SONAR_TOKEN
                            //                 '''
                            //             }
                            //         }
                            //     }
                            // }
                        }
                    }
                }
        // stage('Code Analysis and Testing'){
        //     when {
        //         expression{
        //             params.CodeAnalysisDependencyCheck == true
        //         }
        //     }
        //     parallel{
        //         stage('OWASP Dependency Check'){
        //             steps {
        //                 dependencyCheck additionalArguments: '''
        //                     --scan \'./\' 
        //                     --out \'./\'  
        //                     --format \'ALL\' 
        //                     --disableYarnAudit \
        //                     --prettyPrint''',  nvdCredentialsId: 'NVD-API_KEY', odcInstallation: 'OWASP-DepCheck-10'

        //                 dependencyCheckPublisher failedTotalCritical: 1, pattern: 'dependency-check-report.xml', stopBuild: false
        //             }
        //         }

                
        //     }
        // }
        
        // stage('containerization') {
        //     steps {
        //         script{
        //             sh '''
        //                 EXISTING_IMAGE=$(docker images -q $docker_registry)
        //                 if [ ! -z "$EXISTING_IMAGE" ]; then
        //                     echo "previous build Image '$IMAGE_NAME' found. Removing..."
        //                     docker rmi -f $EXISTING_IMAGE
        //                     echo "previous build image is removed."
        //                 else
        //                     echo "No existing image found for '$IMAGE_NAME'."
        //                 fi
        //                 docker build -t $docker_registry:$GIT_COMMIT .
        //             '''
        //         }
        //     }
        // }

        // stage('Trivy Vulnerability Scanner') {
        //     steps {
        //         sh  ''' 
        //             trivy image $docker_registry:$GIT_COMMIT \
        //                 --severity LOW,MEDIUM,HIGH \
        //                 --exit-code 0 \
        //                 --quiet \
        //                 --format json -o trivy-image-MEDIUM-results.json

        //             trivy image $docker_registry:$GIT_COMMIT \
        //                 --severity CRITICAL \
        //                 --exit-code 1 \
        //                 --quiet \
        //                 --format json -o trivy-image-CRITICAL-results.json
        //         '''
        //     }
        //     post {
        //         always {
        //             sh '''
        //                 trivy convert \
        //                     --format template --template "@/usr/local/share/trivy/templates/html.tpl" \
        //                     --output trivy-image-MEDIUM-results.html trivy-image-MEDIUM-results.json 

        //                 trivy convert \
        //                     --format template --template "@/usr/local/share/trivy/templates/html.tpl" \
        //                     --output trivy-image-CRITICAL-results.html trivy-image-CRITICAL-results.json

        //                 trivy convert \
        //                     --format template --template "@/usr/local/share/trivy/templates/junit.tpl" \
        //                     --output trivy-image-MEDIUM-results.xml  trivy-image-MEDIUM-results.json 

        //                 trivy convert \
        //                     --format template --template "@/usr/local/share/trivy/templates/junit.tpl" \
        //                     --output trivy-image-CRITICAL-results.xml trivy-image-CRITICAL-results.json          
        //             '''
        //         }
        //     }
        // }

        // stage('Publish Docker Image') {
        //     steps {
        //         sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
        //         sh "docker push $docker_registry:$GIT_COMMIT"
        //     }       
        // }

        // stage('Deploy to Development') {
        //     environment {
        //         DEV_STAGE_INSTANCE_IP= ''
        //     }
        //     stages {
        //         stage('initialize-Dev-Stage Instance') {
        //             steps{
        //                 dir('/var/lib/jenkins/workspace/fusion/Fusion-Backend/terraform'){
        //                     sh '''
        //                         set -e
        //                         echo "Initializing Terraform..."
        //                         terraform init -reconfigure
        //                         echo "Applying Terraform configuration..."
        //                         terraform apply --auto-approve
        //                     '''
        //                 }
        //             }
        //         }
        //         // stage('Deploy - Dev-Stage Instance') {
        //         //     steps {
        //         //         script{
        //         //             // Fetch AWS instance IP
        //         //             withAWS(credentials: 'aws-fusion-dev-deploy', region: 'us-east-1') {
        //         //                 DEV_STAGE_INSTANCE_IP = sh(
        //         //                     script: "aws ec2 describe-instances --query 'Reservations[].Instances[].PublicIpAddress' --filters Name=tag:Name,Values=Dev-Backend-Server --output text",
        //         //                     returnStdout: true
        //         //                 ).trim()
        //         //             }
        //         //             sshagent(['dev-deploy-ec2-instance']) {
        //         //                 sh """
        //         //                     ssh -o StrictHostKeyChecking=no ec2-user@${DEV_STAGE_INSTANCE_IP} "
        //         //                         // echo "Cleaning up old containers..."
        //         //                         // docker ps -aq | xargs -r docker rm -f
        //         //                         echo "Running new Docker container..."
        //         //                         docker run -d -p 8080:8080 ${docker_registry}:${GIT_COMMIT}
        //         //                     "
        //         //                 """
        //         //             }
                            
        //         //         }
        //         //     }   
        //         // }
        //     }
        // }
    }

    post { 
        always { 
            deleteDir()
        }    
          
    }
}