package demoObject;

import java.util.Date;

/**
 * Created by johnny.ly on 2016/1/19.
 */
public class HcrmXiaoerSettlePerformancePO {


    private Long id;


    private Date gmtCreate;


    private Date gmtModified;


    private Date calDate;


    private String traceXiaoer;


    private Long confirmOrderCount;


    private Long enterOnAverageDuration;


    private Long assignOnAverageDuration;


    private Long createOrderCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getTraceXiaoer() {
        return traceXiaoer;
    }

    public void setTraceXiaoer(String traceXiaoer) {
        this.traceXiaoer = traceXiaoer;
    }

    public Long getConfirmOrderCount() {
        return confirmOrderCount;
    }

    public void setConfirmOrderCount(Long confirmOrderCount) {
        this.confirmOrderCount = confirmOrderCount;
    }

    public Long getEnterOnAverageDuration() {
        return enterOnAverageDuration;
    }

    public void setEnterOnAverageDuration(Long enterOnAverageDuration) {
        this.enterOnAverageDuration = enterOnAverageDuration;
    }

    public Long getAssignOnAverageDuration() {
        return assignOnAverageDuration;
    }

    public void setAssignOnAverageDuration(Long assignOnAverageDuration) {
        this.assignOnAverageDuration = assignOnAverageDuration;
    }

    public Long getCreateOrderCount() {
        return createOrderCount;
    }

    public void setCreateOrderCount(Long createOrderCount) {
        this.createOrderCount = createOrderCount;
    }
}
