package be.ictdynamic.mobiscan;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by wvdbrand on 3/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MobiscanApplication.class)
@WebAppConfiguration
@Ignore
public class PersistRequestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(  MediaType.APPLICATION_JSON.getType(),
                                                    MediaType.APPLICATION_JSON.getSubtype(),
                                                    Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void greeting() {
    }

//    @Test
//    @Ignore
//    public void greeting() throws Exception {
//        mockMvc.perform(get("/greeting/")
//                                    .param("commune", "Antwerpen")
//                                    .contentType(contentType))
//                                    .andDo(print())
//                                    .andExpect(status().isOk())
//                                    .andExpect(content().json("{'content':'You are from Antwerpen!'}"));
//    }

//    @Test
//    public void updateSystemParameter() throws Exception {
//        String jsonContent = "{\n" +
//                "  \"parameterValue\": \"system parameter DUMMY has been modified\"\n" +
//                "}";
//
//        mockMvc.perform(put("/systemParameters/DUMMY")
//                .contentType(contentType)
//                .content(jsonContent))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        assertThat("The system parameter DUMMY should have been modified", "system parameter DUMMY has been modified", is(systemParameterConfig.getSystemParameters().get("DUMMY")));
//    }

}
