/*
package com.sadad.doctorappointment.user.web.admin;

import com.sadad.doctorappointment.ApplicationTests;
import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.CompletableFuture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class AdminDoctorControllerTest extends ApplicationTests {



    private DoctorDto validDoctorDto;
    private DoctorDto validDoctorDtoForUpdate;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        validDoctorDto = new DoctorDto();
        validDoctorDto.setFirstName("متولیان");
        validDoctorDto.setEmail("motavalian@gmail.com");
        validDoctorDto.setPhoneNumber("09129231440");
        validDoctorDto.setSpecialization("جراح");
        validDoctorDto.setStartWorkTime("07:00");
        validDoctorDto.setEndWorkTime("17:00");

        validDoctorDtoForUpdate = new DoctorDto();
        validDoctorDtoForUpdate.setId(1L);
        validDoctorDtoForUpdate.setFirstName("متولیان");
        validDoctorDtoForUpdate.setEmail("motavalian@gmail.com");
        validDoctorDtoForUpdate.setPhoneNumber("09364170091");
        validDoctorDtoForUpdate.setSpecialization("جراح");
        validDoctorDtoForUpdate.setStartWorkTime("07:00");
        validDoctorDtoForUpdate.setEndWorkTime("17:00");

    }

    @Test
    @Order(0)
    @WithMockUser(username = "doctor",password = "123" ,roles = {"DOCTOR"})
    public void saveDoctor_Success() throws Exception {
        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void UpdateDoctor_Success() throws Exception {
       */
/* mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));*//*


        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDtoForUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.phoneNumber").value("09364170091"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void UpdateDoctor_NotExistsDoctor() throws Exception {
        var dto = new DoctorDto();
        dto.setId(100000L);
        dto.setFirstName("متولیان");
        dto.setEmail("motavalian@gmail.com");
        dto.setPhoneNumber("09364170091");
        dto.setSpecialization("جراح");
        dto.setStartWorkTime("07:00");
        dto.setEndWorkTime("17:00");

        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("doctor.not.found.exception"));
    }
*/
/*    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void saveDoctor_UserRole_Forbidden() throws Exception {
       // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validDoctorDto)))
                .andExpect(status().isForbidden());
    }*//*


    @Test
    public void saveDoctor_InvalidTimeRange() throws Exception {
        DoctorDto invalidDoctorDto = new DoctorDto();
        invalidDoctorDto.setFirstName("متولیان");
        invalidDoctorDto.setEmail("motavalian@gmail.com");
        invalidDoctorDto.setPhoneNumber("09364170091");
        invalidDoctorDto.setSpecialization("جراح");
        invalidDoctorDto.setStartWorkTime("18:00");
        invalidDoctorDto.setEndWorkTime("08:00");
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDoctorDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("doctor.FromTime.isAfter.ToTime"));


    }

    @Test
    public void saveDoctor_ValidationError() throws Exception {
        var dto = new DoctorDto();
        dto.setFirstName(null);
        dto.setEmail("motavalian@gmail.com");
        dto.setPhoneNumber("09364170091");
        dto.setSpecialization("جراح");
        dto.setStartWorkTime("18:000");
        dto.setEndWorkTime("08:000");
        mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.error").value("validation.method.argument.not.valid"));
    }


    @Test
    public void updateDoctor_ConcurrentRequests_RaceCondition() {
       */
/* mockMvc.perform(post("/admin/doctor/saveDoctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDoctorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));*//*


        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                var updateDto = new DoctorDto();
                updateDto.setId(1L);
                updateDto.setFirstName("متولیان");
                updateDto.setEmail("motavalian@gmail.com");
                updateDto.setPhoneNumber("09364170091");
                updateDto.setSpecialization("جراح");
                updateDto.setStartWorkTime("07:00");
                updateDto.setEndWorkTime("17:00");

                var res = mockMvc.perform(post("/admin/doctor/saveDoctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value("1"))
                        .andExpect(jsonPath("$.phoneNumber").value("09364170091"))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
                System.out.println("future1 runAsync ");
                log.info(res);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, taskExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                var updateDto = new DoctorDto();
                updateDto.setId(1L);
                updateDto.setFirstName("متولیان");
                updateDto.setEmail("motavalian@gmail.com");
                updateDto.setPhoneNumber("09121234567");
                updateDto.setSpecialization("جراح");
                updateDto.setStartWorkTime("07:00");
                updateDto.setEndWorkTime("17:00");
                var res = mockMvc.perform(post("/admin/doctor/saveDoctor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value("1"))
                        .andExpect(jsonPath("$.phoneNumber").value("09121234567"))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();
                System.out.println("future2 runAsync ");

                log.info(res);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, taskExecutor);

        CompletableFuture.allOf(future1, future2).join();

    }


}
*/
