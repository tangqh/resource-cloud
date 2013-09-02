package cn.edu.sdu.drs.web.bean;

public class FileUploadBean {
	
	private byte[] file;
	
	public FileUploadBean(){}

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

}
