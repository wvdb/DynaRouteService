package be.ictdynamic.dynarouteservice;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by wvdbrand on 3/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DynarouteserviceApplication.class)
@WebAppConfiguration
public class DynaRouteServiceControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void greeting() throws Exception {
        mockMvc.perform(get("/greeting/")
                                    .param("commune", "Antwerpen")
                                    .contentType(contentType))
                                    .andDo(print())
                                    .andExpect(status().isOk())
                                    .andExpect(content().json("{'content':'You are from Antwerpen!'}"));
//        assertThat("The content should match", content, is("You are from Antwerpen!"));
    }
}
