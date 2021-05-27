package com.sgokcen.dbcontrol.server.dto;

public interface DataTransferObject {
    public Long getPk();
    public String getUniqueId();
    public void setPk(Long pk);
    public void setUniqueId(String uniqueId);
}
