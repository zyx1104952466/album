/**
 * 
 */
package xgu.jkx.fupeng.exception;


/**
 * @author FuPeng
 *
 * Jul 27, 2009
 */
public class NullSourceException extends RuntimeException{

	/**
	 * Դ�ļ�Ϊ���쳣
	 */
	private static final long serialVersionUID = -7686232099308903880L;
	
	public NullSourceException(String msg) {
		super(msg);
	}
	
	public NullSourceException() {
		super();
	}
	
}
