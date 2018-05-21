package com.wwwarehouse.xdw.datasync.web.controller;

import com.wwwarehouse.push.client.PushTarget;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by huaheng.lin on 2018/5/21.
 */
@Transactional
public class DoSomeThingTest{

    @Resource
    private PushTarget pushTarget;

    @Test
    public void justTO() {
        List<Long> longs = new ArrayList<>();
        addList(longs);
        for (Long i : longs) {
            System.out.println(i);
        }
//        addTList(longs);
//        for(Long i : longs){
//            System.out.println(i);
//        }

    }

    private void addList(List<Long> longs) {
        longs.add(1L);
        longs.add(2L);
        longs.add(3L);
        longs.add(4L);
    }

    private List<Long> addTList(List<Long> longs) {
        longs.add(1L);
        longs.add(2L);
        longs.add(3L);
        longs.add(4L);
        return longs;
    }


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Calendar startDate = Calendar.getInstance();
    private static Calendar endDate = Calendar.getInstance();

    public static int yearsBetween(String start, String end) throws ParseException {
        startDate.setTime(sdf.parse(start));
        endDate.setTime(sdf.parse(end));
        return (endDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR));
    }

    public static void main(String[] args) throws ParseException {
        int x = yearsBetween("2018-01-11", "2019-01-10");
        System.out.println("相差" + x + "年");
    }

    //吧ssh的修改为https的，

}