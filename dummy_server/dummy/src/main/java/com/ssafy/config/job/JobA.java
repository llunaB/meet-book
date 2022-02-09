package com.ssafy.config.job;

import com.ssafy.db.entity.Bookmark;
import com.ssafy.db.repository.BookmarkRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class JobA extends QuartzJobBean {

    private JobKey jobKey = null;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    BookmarkRepository repository;

    @Autowired
    private EmailService emailService;
    @Override //JobExecutionContext scheduler, trigger, jobdetail을 포함한 인스턴스를 제공하는 객체
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        jobKey = jobExecutionContext.getJobDetail().getKey();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 1);
        String nowDate = sdf.format(cal.getTime());
        try{
            List<Bookmark> b = repository.findByDate(nowDate);
            for(Bookmark bo : b){
                emailService.createMail(bo.getConference(),bo.getUser());
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
