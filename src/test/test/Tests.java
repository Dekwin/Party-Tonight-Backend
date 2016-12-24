import com.partymaker.mvc.configuration.ApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by anton on 21/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationContext.class})
@WebAppConfiguration
public class Tests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    private String url = "/user";

    @Test
    public void testSignIn() throws Exception {
        MvcResult result = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getAsyncResult());
    }

    @Test
    public void testPhoto() throws Exception {
        Path file = Paths.get("/home/anton/deploy/myimage.jpeg");
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MockMultipartFile multipartFile =
                new MockMultipartFile("nameFile", bytes);

        System.out.println("Start to send file");
        mvc.perform(fileUpload("/maker/event/image").file(multipartFile))
                .andReturn();
    }
}
