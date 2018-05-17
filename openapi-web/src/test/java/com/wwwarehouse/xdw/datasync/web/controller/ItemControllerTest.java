//package com.wwwarehouse.xdw.openapi.web.controller;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
///**
// * Created by shisheng.wang on 17/6/7.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:spring-test.xml", "classpath:spring-config-mvc.xml"})
//@WebAppConfiguration
//public class ItemControllerTest {
//
//    protected MockMvc mvc;
//
//    @Autowired
//    protected WebApplicationContext wac;
//
//    @Before
//    public void init(){
//        if (wac != null && mvc == null) {
//            mvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        }
//    }
//
//    @Test
//    public void getTest() throws Exception {
//        String s = mvc.perform(MockMvcRequestBuilders.get("/item/get").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
//        System.out.println(s);
//    }
//}