package iscas.tca.ake.demoapp.mvc.module.bulletin.interfaces;

import iscas.tca.ake.veap.calculate.GroupData;
import iscas.tca.ake.veap.calculate.GroupInput;

/**
 * 描述：<将Group的计算结果进行公告和获取>
 * @author zn
 * @CreateTime 2014-9-11上午9:44:41
 */
/*
 * Copyright (c) 20014-2041 Institute Of Software Chinese Academy Of Sciences
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @Organization: http://tca.iscas.ac.cn/
 * @author: Nan Zhou
 * @Aknowledge: Tutor Liwu Zhang , Alumnus Yan Zhang, Zhigang Gao
 * @Email: changchangge123@qq.com
 */
public interface IfcBulletinVEAPServer {
	/**
	 * TODO:<向公告板公布数据，并返回公布的数据> 
	 */
//	public GroupData putonBoard(GroupData data);
//	
//	/**
//	 * TODO:<在服务器上寻找groupID所对应的GroupData>
//	 * @param groupID
//	 * @return null 没找到
//	 */
	public GroupData getGroupData(String groupID)throws Exception;
	
//	/**
//	 * TODO:<计算并发布GroupData>
//	 * @param gi Group的输入数据
//	 * @return 计算的结果
//	 */
//	GroupData calAndPutonBoard(GroupInput gi);
}
