node {
    
    stage('Git Clone') { // for display purposes
        // Get some code from a GitHub repository
         git credentialsId: 'GIT_HUB_CREDENTIALS', url: 'https://github.com/mariuss97/springboot-with-docker.git'
    }
    
    stage('Gradle Build') {

       sh './gradlew build'

    } 
    
    stage("Docker build and tag"){
        sh 'docker version'
        sh 'docker build -t jhooq-docker-demo .'
        sh 'docker image list'
        sh 'docker tag jhooq-docker-demo mariuss97/jhooq-docker-demo:latest'
    } 
    
    stage("Docker Login"){
        withCredentials([string(credentialsId: 'DOCKER_HUB_PASSWORD', variable: 'PASSWORD')]) {
            sh 'docker login -u mariuss97 -p $PASSWORD'
        }
    } 
    
    stage("Push Image to Docker Hub"){
        sh 'docker push  mariuss97/jhooq-docker-demo:latest'
    }
    
    stage("SSH Into k8s Server") {
        def remote = [:]
        remote.name = 'K8S master'
        remote.host = '100.0.0.2'
        remote.user = 'vagrant'
        remote.password = 'vagrant'
        remote.allowAnyHosts = true
        
        stage('Put k8s-spring-boot-deployment.yml onto k8smaster') {
            sshPut remote: remote, from: 'k8s-spring-boot-deployment.yml', into: '.'
        }
    
        stage('Deploy spring boot') {
          sshCommand remote: remote, command: "kubectl apply -f k8s-spring-boot-deployment.yml"
        }
        
        # Hack to pull new image during creation of new Pods
        stage('Start new Rollout') {
          sshCommand remote: remote, command: "kubectl rollout restart deployment.apps/jhooq-springboot"
        }
        
        
    } 
}
