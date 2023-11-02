package com.vention.trackloader.scheduler;

import com.vention.trackloader.exceptions.BadRequestException;
import com.vention.trackloader.utils.Utils;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

@WebListener
public class Listener implements ServletContextListener {
    private Scheduler scheduler;
    private static final Logger log = LoggerFactory.getLogger(Listener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String updateAt = Utils.getSchedulerTime();
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();

            JobDetail job = JobBuilder.newJob(LoadData.class).build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule(updateAt)
                            .inTimeZone(TimeZone.getTimeZone("Asia/Tashkent")))
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            log.error("Error initializing Quartz Scheduler", e);
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (scheduler != null) {
            try {
                scheduler.shutdown(true);
            } catch (SchedulerException e) {
                log.error("Error shutting down Quartz Scheduler", e);
                throw new BadRequestException(e.getMessage());
            }
        }
    }
}