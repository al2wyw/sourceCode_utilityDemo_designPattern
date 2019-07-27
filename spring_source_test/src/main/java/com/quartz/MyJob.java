package com.quartz;

import com.utils.LoggerUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/7/26
 * Time: 15:55
 * Desc:
 */
public class MyJob implements org.quartz.Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LoggerUtils.getLogger().info("job2 start ");
    }
}
