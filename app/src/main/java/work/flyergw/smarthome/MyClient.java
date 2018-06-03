package work.flyergw.smarthome;

import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MyClient {

    private Socket socket;
    private DatagramSocket datagramSocket;
    private String TAG = "===>";
    /**
     * 服务端的ip
     */
    private String mDstName;
    /**
     * 服务端端口号
     */
    private int mDesPort;

    //private ConnectLinstener mListener;


    public MyClient(String dstName, int dstPort) throws IOException {
        this.mDstName = dstName;
        this.mDesPort = dstPort;
        try {
            socket = new Socket(dstName, dstPort);
            Log.e("TAG", "连接" + socket);
            InputStream inputStream = new FileInputStream("a.txt");
            OutputStream outputStream = socket.getOutputStream();
            byte buffer[] = new byte[4 * 1024];
            int temp = 0;
            while ((temp = inputStream.read(buffer)) != -1) {
                // 把数据写入到OuputStream对象中
                outputStream.write(buffer, 0, temp);
            }
            // 发送读取的数据到服务端
            outputStream.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        try {
            datagramSocket = new DatagramSocket(dstPort);
            InetAddress serverAddress = InetAddress.getByName(dstName);
            String str = "[2143213;21343fjks;213]";//设置要发送的报文
            byte data[] = str.getBytes();//把字符串str字符串转换为字节数组
            //创建一个DatagramPacket对象，用于发送数据。
            //参数一：要发送的数据  参数二：数据的长度  参数三：服务端的网络地址  参数四：服务器端端口号
            DatagramPacket outPacket = new DatagramPacket(data, data.length, serverAddress, 10025);
            datagramSocket.send(outPacket);//把数据发送到服务端。
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 与服务端进行连接
     *
     * @throws IOException
     */
/*    public void connect() throws IOException {
        if (socket == null) {
            socket = new Socket(mDstName, mDesPort);
        }

        //获取其他客户端发送过来的数据
        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(buffer)) != -1) {
            String data = new String(buffer, 0, len);

            //通过回调接口将获取到的数据推送出去
            if (mListener != null) {
                mListener.onReceiveData(data);
            }
        }
    }

    *//**
     * 认证方法，这个方法是用来进行客户端一对一发送消息的
     * 在实际项目中进行即时通讯时都需要进行登录，这里就是
     * 模拟客户端的账号
     *
     * @param authName
     *//*
    public void auth(String authName) throws IOException {
        if (socket != null) {
            //将客户端账号发送给服务端，让服务端保存
            OutputStream outputStream = socket.getOutputStream();
            //模拟认证格式，以#开头
            outputStream.write(("#" + authName).getBytes());
        }
    }

    *//**
     * 将数据发送给指定的接收者
     *
     * @param receiver 信息接数者
     * @param data     需要发送的内容
     *//*
    public void send(String receiver, String data) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        //模拟内容格式：receiver+  # + content
        outputStream.write((receiver + "#" + data).getBytes());
    }

    *//**
     * 断开连接
     *
     * @throws IOException
     *//*
    public void disconnect() throws IOException {
        if (socket != null) {
            socket.close();
            socket = null;
        }
    }


    public void setOnConnectLinstener(ConnectLinstener linstener) {
        this.mListener = linstener;
    }

    *//**
     * 数据接收回调接口
     *//*
    public interface ConnectLinstener {
        void onReceiveData(String data);
    }*/
}



