package com.lsy.test.user.controller;

import com.lsy.test.system.common.util.DateUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HostModeController {

	private static Integer getMode(List<HostGroupModeDTO> list, String hostGroupId) {
		Set<Integer> hs = new HashSet();
		for (HostGroupModeDTO h : list) {
			hs.add(isEffectiveDate(h.getStartTime(), h.getEndTime(), h.getModeType()));
		}
		hs.remove(-1);
		System.out.println(hs);
		if (hs.size() > 1) {
			System.out.println(1);
		}
		while (hs.iterator().hasNext()) {
			return hs.iterator().next();
		}
		return -1;
	}

	private static Integer isEffectiveDate(String startTime, String endTime, Integer modeType) {
		String nowTime = DateUtil.getDate(DateUtil.MM_DD_HH_MM);
		Date nowDate = DateUtil.parseToDate(nowTime, DateUtil.MM_DD_HH_MM);
		Date startDate = DateUtil.parseToDate(startTime, DateUtil.MM_DD_HH_MM);
		Date endDate = DateUtil.parseToDate(endTime, DateUtil.MM_DD_HH_MM);

		if (nowDate.getTime() == startDate.getTime() ){
			return modeType;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startDate);

		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		if (date.after(begin) && date.before(end)) {
			return modeType;
		} else {
			return -1;
		}
	}

	public static void main(String[] args) {
		List<HostGroupModeDTO> list=new ArrayList<>();
		HostGroupModeDTO hostGroupModeDTO1=new HostGroupModeDTO();
		hostGroupModeDTO1.setStartTime("04-15 15:00");
		hostGroupModeDTO1.setEndTime("04-16 15:00");
		hostGroupModeDTO1.setModeType(1);
		list.add(hostGroupModeDTO1);
		HostGroupModeDTO hostGroupModeDTO2=new HostGroupModeDTO();
		hostGroupModeDTO2.setStartTime("04-22 00:00");
		hostGroupModeDTO2.setEndTime("04-30 00:00");
		hostGroupModeDTO2.setModeType(0);
		list.add(hostGroupModeDTO2);
		HostGroupModeDTO hostGroupModeDTO3=new HostGroupModeDTO();
		hostGroupModeDTO3.setStartTime("04-30 00:00");
		hostGroupModeDTO3.setEndTime("05-01 00:00");
		hostGroupModeDTO3.setModeType(0);
		list.add(hostGroupModeDTO3);
		HostGroupModeDTO hostGroupModeDTO4=new HostGroupModeDTO();
		hostGroupModeDTO4.setStartTime("05-01 00:00");
		hostGroupModeDTO4.setEndTime("05-02 00:00");
		hostGroupModeDTO4.setModeType(1);
		list.add(hostGroupModeDTO4);
		HostGroupModeDTO hostGroupModeDTO5=new HostGroupModeDTO();
		hostGroupModeDTO5.setStartTime("05-04 00:00");
		hostGroupModeDTO5.setEndTime("05-05 00:00");
		hostGroupModeDTO5.setModeType(1);
		list.add(hostGroupModeDTO5);
		HostGroupModeDTO hostGroupModeDTO6=new HostGroupModeDTO();
		hostGroupModeDTO6.setStartTime("05-05 00:00");
		hostGroupModeDTO6.setEndTime("05-06 00:00");
		hostGroupModeDTO6.setModeType(1);
		list.add(hostGroupModeDTO6);
		HostGroupModeDTO hostGroupModeDTO7=new HostGroupModeDTO();
		hostGroupModeDTO7.setStartTime("05-06 00:00");
		hostGroupModeDTO7.setEndTime("05-07 00:00");
		hostGroupModeDTO7.setModeType(0);
		list.add(hostGroupModeDTO7);
		HostGroupModeDTO hostGroupModeDTO8=new HostGroupModeDTO();
		hostGroupModeDTO8.setStartTime("05-07 00:00");
		hostGroupModeDTO8.setEndTime("05-09 00:00");
		hostGroupModeDTO8.setModeType(0);
		list.add(hostGroupModeDTO8);
		HostGroupModeDTO hostGroupModeDTO9=new HostGroupModeDTO();
		hostGroupModeDTO9.setStartTime("05-10 00:00");
		hostGroupModeDTO9.setEndTime("05-11 00:00");
		hostGroupModeDTO9.setModeType(0);
		list.add(hostGroupModeDTO9);
		HostGroupModeDTO hostGroupModeDTO10=new HostGroupModeDTO();
		hostGroupModeDTO10.setStartTime("05-12 00:00");
		hostGroupModeDTO10.setEndTime("05-13 00:00");
		hostGroupModeDTO10.setModeType(1);
		list.add(hostGroupModeDTO10);
		HostGroupModeDTO hostGroupModeDTO11=new HostGroupModeDTO();
		hostGroupModeDTO11.setStartTime("05-13 00:00");
		hostGroupModeDTO11.setEndTime("05-14 00:00");
		hostGroupModeDTO11.setModeType(1);
		list.add(hostGroupModeDTO11);
		HostGroupModeDTO hostGroupModeDTO12=new HostGroupModeDTO();
		hostGroupModeDTO12.setStartTime("05-14 00:00");
		hostGroupModeDTO12.setEndTime("05-15 00:00");
		hostGroupModeDTO12.setModeType(0);
		list.add(hostGroupModeDTO12);
		Integer mode = getMode(list, null);
	}
}
