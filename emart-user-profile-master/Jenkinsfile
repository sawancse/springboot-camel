pipeline {
    agent {
            label "maven"
        }
    environment {
        APPLICATION_NAME = 'jenkins-deployment-automation'
        GIT_REPO="https://github.com/DivakaraGitHub/rest-java-dsl.git"
		APP_TEMPLATE_PARAMETERS = './src/main/resources/application.properties'
		TEMPLATE_YAML = './configuration/template.yaml'
        GIT_BRANCH="master"
        DEV_PROJECT = "fuse-on-ocp-164f"
		JOB_NAME = "Jenkins-Openshift-CICD";
    }
	tools {
        maven 'M3'
    }
    options {
        // set a timeout of 20 Minutes for this pipeline
        timeout(time: 20, unit: 'MINUTES')
        }
    stages {
      stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
       //stage("Checkout") {
             //steps {      
                 //git branch: "${GIT_BRANCH}", url: "${GIT_REPO}"
             //}
           //}
		stage("Test") {
             steps {              
                 sh "mvn test"
              }
            }
		stage("Create ConfigMap") {
            steps {
                script {
                    openshift.withCluster() {
                        openshift.withProject(env.DEV_PROJECT) {
                             openshift.apply(openshift.raw("create configmap ${APPLICATION_NAME}-cm --dry-run --from-file=${APP_TEMPLATE_PARAMETERS} --output=yaml").actions[0].out)
							 openshift.apply(openshift.process(readFile(file: './configuration/template.yaml')))
                        }
                    }
                }
            }
        }
		
	}
	post {
            failure {
                mail to: 'DK00600384@techmahindra.com', from: 'jenkinsopenshift@techmahindra.com',
                subject: "Jenkins Build: ${env.JOB_NAME} - Failed", 
                body: "Job Failed - \"${env.JOB_NAME}\" for build: ${env.APPLICATION_NAME}\n\n"
            }
        }
}