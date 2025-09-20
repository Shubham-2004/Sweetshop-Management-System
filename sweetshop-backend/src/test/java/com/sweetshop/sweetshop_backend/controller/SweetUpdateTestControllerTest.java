package com.sweetshop.sweetshop_backend.controller;

package com.sweetshop.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SweetController.class)
public class SweetUpdateTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SweetService sweetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testUpdateSweetWillFailInitially() throws Exception {
        Sweet updatedSweet = new Sweet("Chocolate Bar", "Candy", 3.00, 100);

        // This test is expected to fail because the endpoint does not exist.
        when(sweetService.updateSweet(anyString(), any(Sweet.class))).thenReturn(updatedSweet);

        mockMvc.perform(put("/api/sweets/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSweet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(3.00));
    }
}