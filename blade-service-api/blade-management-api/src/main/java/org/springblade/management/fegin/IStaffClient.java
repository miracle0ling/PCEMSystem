package org.springblade.management.fegin;

import org.springblade.core.launch.constant.AppConstant;
import org.springblade.core.tool.api.R;
import org.springblade.management.entity.Staff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	value = "blade-management"
)
public interface IStaffClient {
	String API_PREFIX = "/management";

	/**
	 * 获取用户信息
	 *
	 * @param userId 用户id
	 * @return
	 */
	@PostMapping(value = API_PREFIX + "/addStaff",consumes = "application/json")
	Boolean addStaff(Staff staff);
}
