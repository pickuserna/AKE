package iscas.tca.ake.veap.edcoder;

/**
 * @TODO <encode and decode Message>
 * @author zn
 * @CreateTime 2014-9-11ионГ9:44:05
 */
public interface IfcEDCoder {
	byte[] encrypto(byte[] msg, byte[] key);
	byte[] decrypto(byte[] msg, byte[] key);
	byte[] getMac(byte[]msg, byte[] key);
}
