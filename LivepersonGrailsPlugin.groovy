class LivepersonGrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.0 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def title = "Liveperson Plugin"
    def description = 'Grails liveperson plugin'
    def documentation = "http://grails.org/plugin/liveperson"
    def license = 'APACHE'
    def developers = [
        [name: 'Antony Jones', email: 'aj-desirableobjects.co.uk']
    ]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/desirable-objects/liveperson/issues']
    def scm = [url: 'https://github.com/desirable-objects/liveperson']
    }
}
