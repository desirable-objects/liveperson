class LivepersonGrailsPlugin {
    // the plugin version
    def version = "1.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Liveperson Plugin" // Headline display name of the plugin
    def author = "Antony Jones"
    def authorEmail = "aj-desirableobjects.co.uk"
    def description = '''Grails liveperson plugin'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/liveperson"


}
