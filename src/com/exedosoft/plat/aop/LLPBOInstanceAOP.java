package com.exedosoft.plat.aop;

import com.exedosoft.plat.bo.BOInstance;
import com.exedosoft.plat.bo.DOService;
import com.exedosoft.plat.bo.aop.DOServiceSearchFilterAOP;
import com.exedosoft.plat.util.DOGlobals;

public class LLPBOInstanceAOP implements DOServiceSearchFilterAOP {

	@Override
	public BOInstance excute(DOService aService, BOInstance bi) {
		// TODO Auto-generated method stub
		/*
		 * 接口名称 获取HR简历箱中简历 请求类型 get 请求Url /api/hr/resumebox 接口描述
		 * 根据userid，简历分组、简历状态、分页号等获HR简历箱中的简历信息
		 * 业务逻辑：查询暂存状态的简历返回时显示候选人姓名、联系方式以星号代替。 已下载的简历在发放简历金前不能删除（由前台控制）
		 */
		if (aService.getName().equals("hr_resumebox_get")) {
			bi = getFilterBOInstance(bi);
		}
		/*
		 * 接口名称 HR搜索简历接口 请求类型 get 请求Url /api/hr/resumes 接口描述 收缩 功能： HR搜索简历 业务逻辑：
		 * 根据传入的关键字搜索符合条件的简历返回 。positionuid有值时忽略其他参数，
		 * 用该ID在职位表中查询对应的职位信息，然后提取关键字做简历搜索。 HR简历检索返回数据时
		 * ，候选人名字以姓氏加2个星号的方式格式化。例如王宝 王大宝 均以王** 表示
		 */
		else if (aService.getName().equals("hr_resumes_get")
				|| aService.getName().equals(
						"llp_hr_resumes_helper_autocondition") // /帮助服务
				|| aService.getName().equals(
						"llp_hr_resumes_helper_positionuid") // /帮助服务
		) {
			bi = getFilterBOInstance(bi);
		}

		/*
		 * 接口名称 简历接口 请求类型 get 请求Url /api/resumes 接口描述 收缩
		 * 功能：根据传入的简历ID获取简历。多个ID之间用逗号隔开，可返回多分简历。 业务逻辑：后台返回简历详细信息时 ，
		 * 要判断该份简历是否在当前登录HR的简历箱中
		 * ，如果在简历箱中且简历状态为待处理，或者进入简历箱类型为投递，则显示全部候选人姓名联系方式。否则候选人姓名、联系电话、邮箱以星号代替
		 */
		else if (aService.getName().equals("resumes_get")) {

			try {
				BOInstance user = DOGlobals.getInstance().getSessoinContext()
						.getUser();
				if (user != null) {
					// /登陆着UID
					String linkuid = user.getValue("linkuid");
					if (linkuid == null
							|| !linkuid.equals(bi.getValue("hruid"))) {
						bi = getFilterBOInstance(bi);
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// System.out.println("=====================================");
		// System.out.println("BI::" + bi);
		return bi;
	}

	private BOInstance getFilterBOInstance(BOInstance bi) {

		String fullName = bi.getValue("fullname");
		if (fullName != null && fullName.length() > 0) {
			bi.putValue("fullName", fullName.substring(0, 1) + "**");
		}
		bi.putValue("contactemail", "**");
		bi.putValue("telephone", "**");
		return bi;
	}

}
