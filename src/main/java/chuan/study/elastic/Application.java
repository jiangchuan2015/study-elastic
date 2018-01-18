package chuan.study.elastic;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sound.midi.SoundbankResource;
import java.net.InetAddress;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        query();
    }

    private static void query() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "es-chuan").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

         // SearchResponse response = client.prepareSearch("youshu").setTypes("article").get();
        SearchResponse response = client.prepareSearch("youshu").setTypes("article")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhraseQuery("content", "职场"))                 // Query
                .setFrom(0).setSize(10).setExplain(true)
                .get();
        response.getHits().forEach(hit -> System.out.println(hit.getSourceAsString()));

        //System.out.println(response);

    }

    private static void create() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "es-chuan").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        // 创建json对象, 其中一个创建json的方式
        XContentBuilder article = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", 17926)
                .field("bookId", 121555)
                .field("bookTitle", "职场心理学，让你升职加薪的必备利器")
                .field("packShow", "第01课")
                .field("publishedTime", "2018-01-05 05:00:00")
                .field("title", "职场菜鸟，怎样面对简单重复没技术含量的工作")
                .field("target", "今天墨多先生和你一起分析“蘑菇定律”，职场菜鸟，怎样面对简单重复没技术含量的工作？")
                .field("summary", "“蘑菇定律”，是因为蘑菇的生长是需要养料和水分的，但同时也要避免阳光的直接照射，一般需在阴暗的角落里进行培育，否则，过分的曝光会导致蘑菇夭折。")
                .field("author", "墨多先生")
                .field("content", "\n" +
                        "今天，墨多先生用一则心理学法则，叫做“蘑菇定律”，教你作为职场菜鸟，收起自己的锋芒。\n" +
                        "不管你是职场老人还是职场新人，也许都或多或少都经历过这样一个阶段：拿着比一般水平还要低的工资，不受领导的重视，日复一日的做着没有技术含量、简单而又重复的工作。\n" +
                        "甚至有很多名校毕业的新人，由于放不下自己的大学生的“高姿态”，在长时间被安排以类似平庸的工作后，会产生极其消极的态度，甚至会把自己搞得很委屈，以至于萌生辞职跳槽的念头。\n" +
                        "事实上，这种“像蘑菇一样长在阴暗的角落，长期得不到阳光，也没有肥料，任凭你自生自灭”的阶段，在管理学中有一个定义，我们管它叫做“蘑菇定律”。\n" +
                        "| 心理学的常见法则：“蘑菇定律”\n" +
                        "所谓的“蘑菇定律”，其实指的是在职场中，作为管理角色的组织或个人对待职场新进者的一种管理心态。之所以叫“蘑菇定律”，是因为蘑菇的生长是需要养料和水分的，但同时也要避免阳光的直接照射，一般需在阴暗的角落里进行培育，否则，过分的曝光会导致蘑菇夭折。而且，古代时候的培育技术不发达，所以蘑菇的养料一般为人类和动物的排泄物，虽然不太干净，但却是帮助蘑菇生长的必需品。\n" +
                        "因此，将新人步入职场时遭遇的这段看似不公平待遇的时期，称之为“蘑菇期”，事实上，这种刻意打压的做法，多数时候是管理者有意而为之的。\n" +
                        "| 为什么管理者非要刻意打压新人\n" +
                        "一方面，有句古话“吃得苦中苦，方为人上人”，试想古代的职场关系本质上是一种学徒关系的制度，在刚开始学徒的时候，师傅并不马上教你什么，只是安排学徒工做一些跑堂之类的杂活，不管学徒喜不喜欢，都必须做，而且必须做好。徒弟即没有选择的权力，而且倘若连杂事都做不好，就会有丢掉学徒资格的风险。\n" +
                        "另一方面，在当今的社会，许多刚走出大学校门，初出茅庐的年轻人刚到一个新单位，对工作、对业务、对人事、对环境都不熟悉，不太可能马上胜任重要工作，这时候，经历一段“蘑菇管理”，无论对公司还是对个人都有非常重要的意义。起码对于用人单位来说，不会因为初学者对业务的不熟练而给企业带来不必要的损失。\n" +
                        "所以可以看出，如同蘑菇般生长的经历，对于每个年轻人来说，都像是破茧成蝶前非常重要的一步。这个道理虽然简单，但还是有很多职场老兵依然不能理解。\n" +
                        "作为任何一个职场新人，一定要认清自己的位置，踏踏实实把本职工作做好，等到了一定高度和位置，就自然有机会参与到具体战略的讨论了。\n" +
                        "常言道：少说话，多做事，这对新人更适用。每一个刚开始工作的年轻人都要从最简单的工作做起。如果在开始的工作中满腹牢骚、怨气冲天，那么就会对工作草率行事，从而有可能导致错误的发生。\n" +
                        "时刻记住，人可以通过工作来学习，可以通过工作来获取经验、知识和信心。你对工作投入的热情越多，决心越大，工作效率就越高。当你抱有这样的热情时，上班就不再是一件苦差事，工作就会变成一种乐趣，就会有许多人聘请你做你喜欢做的事。\n" +
                        "最后，我想请大家思考两个问题：如果你正处于职场中的“蘑菇期”，你会从哪几点入手，让自己快速的脱颖而出呢？\n" +
                        "反之，如果你是一个职场老鸟，假设你身为管理者，会从那几点考察一个新人到底是否有能力胜任未来的工作呢？\n" +
                        "第02课 | 怎样才能避免成为埋头苦干却不讨好的人")
                .field("mediaUrl", "http://media2.youshu.cc/readwith/media/121555/5a536d5a9724c.mp3")
                .endObject();


        // 存json入索引中
        IndexResponse response = client.prepareIndex("youshu", "article", "17926").setSource(article).get();

        // 结果获取
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        long version = response.getVersion();

        System.out.println(index + " : " + type + ": " + id + ": " + version);
        System.out.println(response.getResult());

        client.close();
    }
}
