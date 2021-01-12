package org.springblade.management.fegin;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.api.R;
import org.springblade.management.entity.Staff;
import org.springblade.management.service.IStaffService;
import org.springblade.system.user.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StaffClient implements IStaffClient{
	private IStaffService staffService;


	@Override
	public Boolean addStaff(@RequestBody Staff staff) {
		return staffService.addNew(staff);
	}
}
