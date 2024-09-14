package com.sadad.doctorappointment.appointment.web.api;

import com.sadad.doctorappointment.ApplicationTests;
import com.sadad.doctorappointment.appointment.dto.SlotsRequest;
import com.sadad.doctorappointment.doctor.dto.DoctorDto;
import com.sadad.doctorappointment.user.dto.UserDto;
import com.sadad.doctorappointment.user.service.IDoctorService;
import com.sadad.doctorappointment.user.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
class DoctorAppointmentControllerTest extends ApplicationTests {

    @Autowired
    IDoctorService doctorService;

    @Autowired
    UserService userService ;

    private UserDto userDto;
    private DoctorDto doctorDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        userDto = userService.saveOrUpdate(UserDto.builder()
                .firstName("usertest")
                .lastName("lastUserTest")
                .email("test@test.com")
                .password("123")
                .username("test")
                .phoneNumber("09129231440")
                .enabled(true)
                .build());

         doctorDto = doctorService.saveOrUpdate(DoctorDto.builder()
                .userId(userDto.getId())
                .specialization("specialization")
                .startWorkTime("07:00")
                .endWorkTime("18:10")
                .build());
    }
    @Test
    @Order(0)
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void setSlots_Success() throws Exception {


        var request = new SlotsRequest();
        request.setDoctorId(doctorDto.getUserId());
        request.setFromTime("07:00");
        request.setToTime("18:00");
        request.setCurrentDate("2024-09-13");

        var res = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res);
    }

    @Test
    @Order(0)
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void setSlots_TimeRange() throws Exception {


        var request = new SlotsRequest();
        request.setDoctorId(doctorDto.getUserId());
        request.setFromTime("09:00");
        request.setToTime("09:15");
        request.setCurrentDate("2024-09-15");

        var res = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0))
                .andReturn()
                .getResponse()
                .getContentType();

        log.info(res);
    }


    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void setSlots_InvalidTimeRange() throws Exception {


        var request = new SlotsRequest();
        request.setDoctorId(doctorDto.getUserId());
        request.setFromTime("18:00");
        request.setToTime("07:00");
        request.setCurrentDate("2024-09-13");

        var res = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("appointment.FromTime.isAfter.ToTime"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res);
    }

    @Test
    public void setSlots_ValidationError() throws Exception {



        var request = new SlotsRequest();
        request.setDoctorId(doctorDto.getUserId());
        request.setFromTime("18:00");
        request.setToTime("007:00");
        request.setCurrentDate("2024/09/13");

        var res = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .header("Authorization", "Basic dfgdgfdfgdfgd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.error").value("validation.method.argument.not.valid"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res);
    }

    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void setSlots_integrity_violation_error() throws Exception {


        var request = new SlotsRequest();
        request.setDoctorId(doctorDto.getUserId());
        request.setFromTime("07:00");
        request.setToTime("18:00");
        request.setCurrentDate("2024-09-14");

        var res = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var res2 = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("database.data.integrity.violation.exception"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res2);
    }



    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void getAll() throws Exception {

        var request = new SlotsRequest();
        request.setDoctorId(doctorDto.getUserId());
        request.setFromTime("07:00");
        request.setToTime("18:00");
        request.setCurrentDate("2024-09-14");

        var res = mockMvc.perform(post("/api/doctor/appointment/setSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var res2 = mockMvc.perform(get("/api/doctor/appointment/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId" , doctorDto.getUserId().toString())
                        .param("currentDate" , request.getCurrentDate())
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", Matchers.greaterThan(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res2);
    }


    @Test
    @WithMockUser(username = "doctor", roles = {"DOCTOR"})
    public void getAll_empty() throws Exception {


        var res2 = mockMvc.perform(get("/api/doctor/appointment/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("doctorId" , doctorDto.getUserId().toString())
                        .param("currentDate" , "2000-09-11")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()", Matchers.lessThan(1)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        log.info(res2);
    }

}
