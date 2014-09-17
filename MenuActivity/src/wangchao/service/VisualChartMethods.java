package wangchao.service;

import com.special.ResideMenuDemo.R;

import wangchao.voicemodule.*;

public class VisualChartMethods {

	int chart_counter;   //视力表计数器
	String chart_oneresult; //单眼视力
	int countback = 0;//计数器返回值

	/*
     * 行数与视力对应表
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
//	 * 矫正or裸眼？
//	 * 0裸眼，,1矫正
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
     * 随机生成e字方向
     */
    public int direct(){

		int[] directNum = {0,1,2,3};
		int i = (int) (Math.random()*4);		
		return directNum[i];
    }
    /*
     * 临时测试用
     * e字的四方向图片
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
     * 判断方向正误(字符)
     */
    public String toJudge(String str){
        char[] strChar = str.toCharArray();
		String judgeback = "error";
		String context = "";
		for(int i = 0;i < strChar.length;i++)
		{
			if(strChar[i] == '上')
			{
				judgeback = "up";
				break;
			}else if(strChar[i] == '下')
			{
				judgeback = "down";
				break;
			}else if(strChar[i] == '左')
			{
				judgeback = "left";
				break;
			}else if(strChar[i] == '右')
			{
				judgeback = "right";
				break;
			}else if(strChar[i] == '有')
			{
				judgeback = "right";
				break;
			}else if(strChar[i] == '过')
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
     * 输入字符（方向），输出图片id对照表
     */
    public int Direct2Pic(String str){
        char[] strChar = str.toCharArray();
		int judgeback = 101010101;
		String context = "";
		for(int i = 0;i < strChar.length;i++)
		{
			if(strChar[i] == '上')
			{
				judgeback = R.drawable.e_up;
				break;
			}else if(strChar[i] == '下')
			{
				judgeback = R.drawable.e_down;
				break;
			}else if(strChar[i] == '左')
			{
				judgeback = R.drawable.e_left;
				break;
			}else if(strChar[i] == '右')
			{
				judgeback = R.drawable.e_right;
				break;
			}else if(strChar[i] == '有')
			{
				judgeback = R.drawable.e_right;
				break;
			}else if(strChar[i] == '过')
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
    
    int chart_row = 1;//行数
    
    int chart_countert = 0;//计同一行判断正确次数
	int chart_counterf = 0; //计同一行判断错误次数 
    
//    //测试 单次 视力到达哪一行了
//    //返回行数，返回0则代表没有结束
//    //输入为正误的判断值，0为错，1为对
//    public int VisualRow(int judge_tf)
//    {
//    	int visualrow = 0;//行数返回值，只有在得出结果时有返回值，否则为0
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
     * 简化的方法，对了进入下一行，错了2次结束
     */
    public int VisualRowSimp(int judge_tf)
    {
    	int visualrow = 0;//行数返回值，只有在得出结果时有返回值，否则为0
    	
    	if(chart_row > 14)
    	{
    		visualrow = chart_row;
    		chart_counterf = 0;
			chart_countert = 0;
			chart_row = 1;
    	}else if(judge_tf == 0)
    	{
    		chart_counterf += 1;
    		chart_countert = 0;
    		
			if(chart_counterf == 2)
			{
				visualrow = chart_row;
				chart_counterf = 0;
				chart_countert = 0;				
				chart_row = 1;
				System.out.println("resultfromvcm:  "+visualrow);			
			}
    	}
		 else if(judge_tf == 1)
		{
			chart_countert += 1;
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
    * 图片缩小的比例
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