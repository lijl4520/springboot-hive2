package com.embraces.hive.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
/**
 * @Author Lijl
 * @ClassName SFTPUtils
 * @Description 解释一下SFTP的整个调用过程，这个过程就是通过Ip、Port、Username、Password获取一个Session,
 * 然后通过Session打开SFTP通道（获得SFTP Channel对象）,再在建立通道（Channel）连接，最后我们就是
 * 通过这个Channel对象来调用SFTP的各种操作方法.总是要记得，我们操作完SFTP需要手动断开Channel连接与Session连接。
 * @Date 2020/10/26 9:23
 * @Version 1.0
 */

public class SFTPUtils
{
    private static Logger log = Logger.getLogger(SFTPUtils.class.getName());
    //服务器连接ip
    private String host;
    //用户名
    private String username;
    //密码
    private String password;
    //端口号
    private int port = 22;
    private ChannelSftp sftp = null;
    private Session sshSession = null;

    public SFTPUtils(String host, int port, String username, String password)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.connect();
    }

    /**
     * 通过SFTP连接服务器
     */
    public void connect()
    {
        try
        {
            JSch jsch = new JSch();
            this.sshSession = jsch.getSession(username, host, port);
            if (log.isInfoEnabled())
            {
                log.info("Session created.");
            }
            this.sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            this.sshSession.setConfig(sshConfig);
            this.sshSession.connect();
            if (log.isInfoEnabled())
            {
                log.info("Session connected.");
            }
            Channel channel = this.sshSession.openChannel("sftp");
            channel.connect();
            if (log.isInfoEnabled())
            {
                log.info("Opening Channel.");
            }
            this.sftp = (ChannelSftp) channel;
            if (log.isInfoEnabled())
            {
                log.info("Connected to " + this.host + ".");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect()
    {
        if (this.sftp != null)
        {
            if (this.sftp.isConnected())
            {
                this.sftp.disconnect();
                if (log.isInfoEnabled())
                {
                    log.info("sftp is closed already");
                }
            }
        }
        if (this.sshSession != null)
        {
            if (this.sshSession.isConnected())
            {
                this.sshSession.disconnect();
                if (log.isInfoEnabled())
                {
                    log.info("sshSession is closed already");
                }
            }
        }
    }

    /**
     * 上传单个文件
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param localPath：本地上传目录(以路径符号结束)
     * @param localFileName：上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName,String localPath, String localFileName)
    {
        FileInputStream in = null;
        try
        {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    /**
     * 删除本地文件
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            return false;
        }

        if (!file.isFile())
        {
            return false;
        }
        boolean rs = file.delete();
        if (rs && log.isInfoEnabled())
        {
            log.info("delete file success from local.");
        }
        return rs;
    }

    /**
     * 创建目录
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath)
    {
        try
        {
            if (isDirExist(createpath))
            {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry)
            {
                if (path.equals(""))
                {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString()))
                {
                    sftp.cd(filePath.toString());
                }
                else
                {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }

            }
            this.sftp.cd(createpath);
            return true;
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断目录是否存在
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory)
    {
        boolean isDirExistFlag = false;
        try
        {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }
        catch (Exception e)
        {
            if (e.getMessage().toLowerCase().equals("no such file"))
            {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }


    /**
     * 如果目录不存在就创建目录
     * @param path
     */
    public void mkdirs(String path)
    {
        File f = new File(path);

        String fs = f.getParent();

        f = new File(fs);

        if (!f.exists())
        {
            f.mkdirs();
        }
    }
}
