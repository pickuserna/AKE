package iscas.tca.ake;

import iscas.tca.ake.message.IfcMessage;

/**
 * ������<AKEЭ���ʹ�ýӿ�>
 * @author zn
 * @CreateTime 2014-8-21����3:16:06
 */
public interface IfcAkeProtocol {
	/**
	 * TODO:<ʹ��init��client��server���г�ʼ��>
	 * @param init:<��ʼ������>
	 * @return �Ƿ�ɹ�
	 */
	public boolean init(IfcInitData init)throws Exception;

	/**
	 * TODO:<Э���������ʵ��,����Э��������Ϣ>
	 * @return IfcMessageҪ���͸�����������Ϣ
	 */

	public IfcMessage startProtocol();
	
	/**
	 * TODO:<������Ϣm�������ش����Ľ��>
	 * @param m :Ҫ�������Ϣ
	 * @return IfcMessage����Ľ��
	 */
	public IfcMessage processMessage(IfcMessage m)throws Exception;
	
	/**
	 * TODO:<��Ϣ����֤��ʵ�֣��ж϶Է��Ƿ�ͨ������֤>
	 * @return true ͨ������֤��falseû��ͨ��
	 */
	public boolean isVerified();
	
	/**
	 * TODO:<�ж�Э���Ƿ�����������>
	 * @return 
	 */
	public boolean isProtocolOver();
	
	/**
	 * TODO:<��ȡsk,����޷����ɣ�����null>
	 * @return 
	 */
	public byte[] getsk();
	
	public int getIDNum();
}
