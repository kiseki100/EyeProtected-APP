package wangchao.service;

import com.special.ResideMenuDemo.R;

import wangchao.voicemodule.*;

public class VisualChartMethods {

	int chart_counter;   //�����������
	String chart_oneresult; //��������
	int countback = 0;//����������ֵ

	/*
     * ������������Ӧ��
     */
	public String chart(int row) 
    {
		switch(row)
		{
		case 1:chart_oneresult = "4.0(0.1)";break;
		case 2:chart_oneresult = "4.1(0.12)";break;
		case 3:chart_oneresult = "4.2(0.15)";break;
		case 4:chart_oneresult = "4.3(0.2)";break;
		case 5:chart_oneresult = "4.4(0.25)";break;
		case 6:chart_oneresult = "4.5(0.3)";break;
		case 7:chart_oneresult = "4.6(0.4)";break;
		case 8:chart_oneresult = "4.7(0.5)";break;
		case 9:chart_oneresult = "4.8(0.6)";break;
		case 10:chart_oneresult = "4.9(0.8)";break;
		case 11:chart_oneresult = "5.0(1.0)";break;
		case 12:chart_oneresult = "5.1(1.2)";break;
		case 13:chart_oneresult = "5.2(1.5)";break;
		case 14:chart_oneresult = "5.3(2.0)";break;
		default:chart_oneresult = "error!";
		}
    	return chart_oneresult;		
    }
	
//	/*
//	 * ����or���ۣ�
//	 * 0���ۣ�,1����
//	 */
//	public static int howeye;
//	
//	public void setEye(int eyehow)
//	{
//		this.howeye = eyehow;
//	}
//	
//	public int getEye()
//	{
//		this.howeye = howeye;
//		return howeye;
//	}
	
    /*
     * �������e�ַ���
     */
    public int direct(){

		int[] directNum = {0,1,2,3};
		int i = (int) (Math.random()*4);		
		return directNum[i];
    }
    /*
     * ��ʱ������
     * e�ֵ��ķ���ͼƬ
     */
    public int setImageE(int imageNum){
    	int imageId = 0;
    	switch(imageNum){
    		case 0: 
    			imageId = R.drawable.e_up;break;
    		case 1: 
    			imageId = R.drawable.e_down;break;
    		case 2: 
    			imageId = R.drawable.e_right;break;
    		case 3: 
    			imageId = R.drawable.e_left;break;
    		default:
    			break;
    	}
    	//System.out.println("imageId:"+imageId);
    	return imageId;
    }
    /*
     * �жϷ�������(�ַ�)
     */
    public String toJudge(String str){
        char[] strChar = str.toCharArray();
		String judgeback = "error";
		String context = "";
		for(int i = 0;i < strChar.length;i++)
		{
			if(strChar[i] == '��')
			{
				judgeback = "up";
				break;
			}
			else if(strChar[i] == '��')
			{
				judgeback = "down";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "down";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "left";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "left";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "left";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "right";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "right";
				break;
			}else if(strChar[i] == 'Ӵ')
			{
				judgeback = "right";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "right";
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = "pass";
				break;
			}else
			{
				judgeback = "error";
			}			
		}
		//System.out.println("toJudge:"+judgeback);
		return judgeback;
			
   }
    /*
     * �����ַ������򣩣����ͼƬid���ձ�
     */
    public int Direct2Pic(String str){
        char[] strChar = str.toCharArray();
		int judgeback = 101010101;
		String context = "";
		for(int i = 0;i < strChar.length;i++)
		{
			if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_up;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_down;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_down;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_left;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_left;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_left;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_right;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_right;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = R.drawable.e_right;
				break;
			}else if(strChar[i] == 'Ӵ')
			{
				judgeback = R.drawable.e_right;
				break;
			}else if(strChar[i] == '��')
			{
				judgeback = 101010000;
				break;
			}else
			{
				judgeback = 101010101;
			}			
		}
		//System.out.println("Direct2Pic:"+judgeback);
		return judgeback;
			
   }
    
    int chart_row = 1;//����
    
    int chart_countert = 0;//��ͬһ���ж���ȷ����
	int chart_counterf = 0; //��ͬһ���жϴ������ 
    
//    //���� ���� ����������һ����
//    //��������������0�����û�н���
//    //����Ϊ������ж�ֵ��0Ϊ��1Ϊ��
//    public int VisualRow(int judge_tf)
//    {
//    	int visualrow = 0;//��������ֵ��ֻ���ڵó����ʱ�з���ֵ������Ϊ0
//    	
//    	if(chart_row > 14)
//    	{
//    		visualrow = chart_row;
//    		chart_counterf = 0;
//			chart_countert = 0;
//			chart_row = 1;
//    	}else if(judge_tf == 0)
//    	{
//    		chart_counterf += 1;
//    		chart_countert = 0;
//    		
//			if(chart_counterf == 2)
//			{
//				visualrow = chart_row;
//				chart_counterf = 0;
//				chart_countert = 0;				
//				chart_row = 1;
//				System.out.println("resultfromvcm:  "+visualrow);			
//			}
//    	}
//		 else if(judge_tf == 1)
//		{
//			chart_countert += 1;
//			if(chart_row == 1)
//			{
//				chart_row += 1;
//				chart_countert = 0;
//				chart_counterf = 0;
//			}else if(chart_row != 1)
//			{
//				if(chart_countert == 2)
//					{
//					chart_row += 1;
//					chart_counterf = 0;
//					chart_countert = 0;
//					}
//			}
//		}
//    	
//    	return visualrow;
//    }
    /*
     * �򻯵ķ��������˽�����һ�У�����2�ν���
     */
    public int VisualRowSimp(int judge_tf)
    {
    	int visualrow = 0;//��������ֵ��ֻ���ڵó����ʱ�з���ֵ������Ϊ0
    	
    	if(chart_row > 14)
    	{
    		visualrow = chart_row;
    		chart_counterf = 0;
//			chart_countert = 0;
			chart_row = 1;
    	}else if(judge_tf == 0)//����
    	{
    		chart_counterf += 1;
//    		chart_countert = 0;
    		
			if(chart_counterf == 2)
			{
				visualrow = chart_row;
				chart_counterf = 0;
//				chart_countert = 0;				
				chart_row = 1;
//				System.out.println("resultfromvcm:  "+visualrow);			
			}
    	}
		 else if(judge_tf == 1)//��ȷ
		{
			
			chart_row += 1;
			chart_counterf = 0;
//			if(chart_row == 1)
//			{
//				chart_row += 1;
//				chart_countert = 0;
//				chart_counterf = 0;
//			}else if(chart_row != 1)
//			{
//				if(chart_countert == 2)
//					{
//					chart_row += 1;
//					chart_counterf = 0;
//					chart_countert = 0;
//					}
//			}
		}
    	
    	return visualrow;
    }
    
    public int getchartrow()
    {
    	this.chart_row = chart_row;
    	System.out.println("getchartrow: "+chart_row);
    	return chart_row;
    }
    
   
    /*
    * ͼƬ��С�ı���
    */
    public float Picscale(int row)
    {
    	float scale=1f;
    	for(int i = 0;i < row;i++)
    	{
    		scale = scale * (0.9f);
    	}
    	System.out.println("scale: "+scale);
    	return scale;
    }
}