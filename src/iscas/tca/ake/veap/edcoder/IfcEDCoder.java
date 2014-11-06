package iscas.tca.ake.veap.edcoder;

/**
 * ÃèÊö£º<>
 * @author zn
 * @CreateTime 2014-9-11ÉÏÎç9:44:05
 */
public interface IfcEDCoder {
	byte[] encrypto(byte[] msg, byte[] key);
	byte[] decrypto(byte[] msg, byte[] key);
	byte[] getMac(byte[]msg, byte[] key);
}
