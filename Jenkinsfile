pipeline {
    agent any
    tools {
	jdk 'jdk8'
    }
    stages {
    	stage('Build') 	{
			steps {
        		sh '''
				java -version
				./gradlew -b build.gradle clean build
			'''
			}
    	}
    	stage('parallel stages') {
    		parallel {
    			stage('Archival') {
				    steps {
        				archiveArtifacts 'build/libs/*.jar'
				    }
    			}
                stage('Test cases') {
                    steps {
                        junit 'build/test-results/test/*.xml'
				    }
                }
            }
        }
        stage('Build Image') {
            steps {
                sh '''
                    docker build --no-cache -t product-catalog-image:latest .
                    docker tag product-catalog-image:latest bharadwaja1998/product-catalog-image:v${BUILD_NUMBER}
                '''
            }
        }

        stage('Push Image') {
            steps {
		withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                	sh '''
                    		docker login --username $USER --password $PASS
                    		docker push bharadwaja1998/product-catalog-image:v${BUILD_NUMBER}
                	'''
		}
            }
        }
    }

    post {
        always {
            notify('started')
        }
        failure {
            notify('err')
        }
        success {
            notify('success')
        }
    }    
}

def notify(status){
    emailext (
    to: "chilukuribharadwaja@gmail.com",
    subject: "${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
    body: """<p>${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME}  [${env.BUILD_NUMBER}]</a></p>""",
    )
}

