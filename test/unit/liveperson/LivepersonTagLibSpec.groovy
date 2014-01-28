package liveperson

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import spock.lang.Specification
import spock.lang.Unroll


@Unroll
@TestFor(LivepersonTagLib)
//@TestMixin(GrailsUnitTestMixin)
class LivepersonTagLibSpec extends Specification {

    void setup() {

        config.liveperson = [
                lpTagSrv: 'my.tag.server',
                lpServer: 'my.live.server',
                lpNumber: '12345678',
                deploymentID: 'deployment-id'
        ]

    }

	void "reads configuration from config file and places it into the page"() {
        when:
            String output = applyTemplate '<liveperson:initialize />'

        then:
            output == '''<script type="text/javascript">
    var lpMTagConfig = lpMTagConfig || {};
    lpMTagConfig.vars = lpMTagConfig.vars || [];

    lpMTagConfig.lpTagSrv = "my.tag.server";
    lpMTagConfig.lpServer = "my.live.server";
    lpMTagConfig.lpNumber = "12345678";
    lpMTagConfig.deploymentID = "deployment-id";
</script>'''

	}

    void "reads configuration from attributes and places it into the page"() {
        when:
        String output = applyTemplate '<liveperson:initialize lpTagSrv="x" lpServer="y" lpNumber="1234" deploymentID="z" />'

        then:
        output == '''<script type="text/javascript">
    var lpMTagConfig = lpMTagConfig || {};
    lpMTagConfig.vars = lpMTagConfig.vars || [];

    lpMTagConfig.lpTagSrv = "x";
    lpMTagConfig.lpServer = "y";
    lpMTagConfig.lpNumber = "1234";
    lpMTagConfig.deploymentID = "z";
</script>'''

    }

    void "ensures exception is raised if missing the liveperson configuration is missing"() {

        given:
            config.liveperson = null

        when:
            applyTemplate "<liveperson:initialize />"

        then:
            thrown GrailsTagException
    }

    void "ensure routing tag output"() {

        when:
            String output = applyTemplate("<liveperson:routing>Hello</liveperson:routing>")

        then:
            output == """<script type='text/javascript'>
    lpMTagConfig.vars.push(["page","unit","deployment-id"]);

    Hello
</script>"""

    }

    void "can push variables into the page in #scope scope"() {

        given:
            Map<String, String> vars = [foo: 'bar', baz: 'zed']

        when:
            String output = applyTemplate('<liveperson:push scope="${scope}" variables="${vars}" />', [scope: scope, vars: vars])

        then:
            output == """lpMTagConfig.vars.push(['${scope}', 'foo', 'bar']);
lpMTagConfig.vars.push(['${scope}', 'baz', 'zed']);
"""

        where:
            scope << ['page', 'session']

    }

    void "can coerce variable #realRepresentation into #stringRepresentation"() {

        when:
            String output = applyTemplate('<liveperson:push scope="page" variables="${vars}" />', [vars: [custom: realRepresentation]])

        then:
            output == """lpMTagConfig.vars.push(['page', 'custom', '${stringRepresentation}']);
"""

        where:
            realRepresentation   | stringRepresentation
            5                    | '5'
            true                 | 'true'

    }

    void "writes loadtag to page"() {

        when:
            String output = applyTemplate '<liveperson:loadTag />'

        then:
            output == '''<script type="text/javascript">
    lpMTagConfig.loadTag();
</script>'''

    }

}

