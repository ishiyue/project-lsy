package com.lsy.test.user.controller;

import java.io.Serializable;
import java.util.List;

public class HostGroupModeDTO implements Serializable{
		private static final long serialVersionUID = -3368861887235015044L;


		private Long autoId;
		/**
		 * 商户id
		 */
		private String mcId;
		/**
		 * 起始时间
		 */
		private String startTime;
		/**
		 * 终止时间
		 */
		private String endTime;
		/**
		 * 制冷0、制热1、维护2
		 */
		private Integer modeType;
		/**
		 * 通用设置0，例外设置1
		 */
		private Integer type;
		/**
		 * 适用主机组id
		 */
		private List<String> hostIds;

		@Override
		public String toString() {
			return "HostGroupModeDTO{" +
					"autoId=" + autoId +
					", mcId='" + mcId + '\'' +
					", startTime='" + startTime + '\'' +
					", endTime='" + endTime + '\'' +
					", modeType=" + modeType +
					", type=" + type +
					", hostIds=" + hostIds +
					'}';
		}

		public Long getAutoId() {
			return autoId;
		}

		public void setAutoId(Long autoId) {
			this.autoId = autoId;
		}

		public String getMcId() {
			return mcId;
		}

		public void setMcId(String mcId) {
			this.mcId = mcId;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public Integer getModeType() {
			return modeType;
		}

		public void setModeType(Integer modeType) {
			this.modeType = modeType;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public List<String> getHostIds() {
			return hostIds;
		}

		public void setHostIds(List<String> hostIds) {
			this.hostIds = hostIds;
		}

}
