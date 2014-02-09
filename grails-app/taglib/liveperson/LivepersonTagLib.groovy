package liveperson

import org.codehaus.groovy.grails.commons.GrailsApplication

class LivepersonTagLib {

    GrailsApplication grailsApplication
    static namespace = 'liveperson'

    def initialize = { attrs ->

        if (!grailsApplication.config.containsKey("liveperson") && !attrs) {
            throwTagError "Missing liveperson config"
        }

        ConfigObject livepersonConfig = grailsApplication.config.liveperson

        List<String> config = [
                attrs.lpTagSrv ?: livepersonConfig.lpTagSrv,
                attrs.lpServer ?: livepersonConfig.lpServer,
                attrs.lpNumber ?: livepersonConfig.lpNumber,
                attrs.deploymentID ?: livepersonConfig.deploymentID
        ]

        out << String.format('''<script type="text/javascript">
    var lpMTagConfig = lpMTagConfig || {};
    lpMTagConfig.vars = lpMTagConfig.vars || [];

    lpMTagConfig.lpTagSrv = "%s";
    lpMTagConfig.lpServer = "%s";
    lpMTagConfig.lpNumber = "%s";
    lpMTagConfig.deploymentID = "%s";
</script>''', *config)

    }

    def routing = { attrs, body ->
        out << String.format("""<script type='text/javascript'>
    lpMTagConfig.vars.push(["page","unit","%s"]);

    """, grailsApplication.config.liveperson.deploymentID)

        out << body()

        out << """
</script>"""
    }

    def push = { attrs ->

        String nl = System.getProperty("line.separator")

        attrs.variables.each { String key, val ->
            out << "lpMTagConfig.vars.push(['${attrs.scope}', '${key}', '${val as String}']);"
            out << nl
        }

    }

    def loadTag = {

        out << '''<script type="text/javascript">
    lpMTagConfig.loadTag();
</script>'''

    }

}
