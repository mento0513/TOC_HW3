// HW3 of Theory-of-Computation in NCKU
// Name:���¥�
// Student ID:F74002060
// File description:
// I wrote 2 version of this homework
// one is using JSONArray, the other is without using JSONArray
// to compare the speed difference.
// Example Input:
// http://www.datagarage.io/api/5365dee31bc6e9d9463a0057 �j�w�� �_���n�� 103
// print 0 if there is no data match
import java.io.*;
import java.net.*;
import java.util.Arrays;
import org.json.*;
public class TocHw3
{
		private static String url;
		private static URL data;
    private static URLConnection connect;
    private static BufferedReader in ;
    private static int MAXNUM = 100000;
    private static String ParseJson() throws IOException
    {
    	String inputLine;
    	StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        return a.toString();
    }
		private static int WithoutJson(String City, String Add, String Year) throws IOException
		{
         int c,objectMatch = 0,cnt = 0, num = 1;
         String region[] = new String[MAXNUM];
         String addr[] = new String[MAXNUM];
         String year[] = new String[MAXNUM];
         int value[] = new int[MAXNUM];
         boolean skip[] = new boolean[27];
         Arrays.fill(skip,  Boolean.FALSE);
         skip[1] = skip[3] = skip[8] = skip[22] = true;
         char ch;
         StringBuffer str = new StringBuffer();
         while( (c = in.read()) != -1)
         {
        	 ch = (char) c;
        	 if( ch == '{')
        	 {
        		 objectMatch++;
        		 cnt = 1;
        	 }
        	 else if( ch == '}')
        		 objectMatch = 0;
        	 if( objectMatch == 1 )
        	 {
        		 if( skip[cnt] )
        		 {
        			 int cas;
        			 if( cnt == 8 || cnt == 22) cas = 1;
        			 else cas = 0;
        			 while( ((char)in.read()) != ':' );
        			 str.delete(0, str.length());
        			 if( cas == 0)
        			 {
        				 in.read();
        				 while( (ch =(char)in.read()) != '"')
        					 str.append(ch);
        			 }
        			 else
        			 {
        				 while( (ch =(char)in.read()) != ',')
        					 str.append(ch);
        			 }
        			 if( cnt == 1)
        			 {
        				 if( str.toString().equals(City)) region[num] = str.toString();
        				 else objectMatch = 0;
        			 }
        			 else if( cnt == 3)
        			 {
        				 if( str.toString().contains(Add)) addr[num] = str.toString();
        				 else objectMatch = 0;
        			 }
        			 else if( cnt == 8)
        			 {
        				 if( Integer.parseInt(str.toString()) > Integer.parseInt(Year+"00")) year[num] = str.toString();
        				 else objectMatch = 0;
        			 }
        			 else if( cnt == 22)
        			 {
        				 value[num++] = Integer.parseInt(str.toString());
        				 objectMatch = 0;
        			 }
        			 cnt++;
        		 }
        		 else
        		 {
        			 while( ((char)in.read()) != ',' );
        			 cnt++;
        		 }
        	 }
         }
         in.close();
         int sum = 0;
         for(int i = 1; i<num; i++)
         {
        	 sum += value[i];
        	 //System.out.println(region[i]+" "+addr[i]+" "+year[i]+" "+value[i]);
         }
         if( num == 1) return 0;
         else return (int)sum/(num-1);
		}
		public static void main(String[] args) throws IOException, JSONException
		{
			// setting url 
			url = args[0];
			data = new URL(url);
			connect = data.openConnection();
			in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
		
			// long StartTime = System.currentTimeMillis();
		  System.out.println(WithoutJson(args[1],args[2],args[3]));
			// long ProcessTime = System.currentTimeMillis() - StartTime;/*String tmp = test();
			// System.out.println(ProcessTime/1000);
		   
			/* Using JSONArray
			String data = ParseJson();
			JSONArray Data = new JSONArray(data);
			int total = 0, cnt = 0, year = Integer.parseInt(args[3]+"00"), date;
			String city,add;
			for(int i=0;i<Data.length();i++)
			{
				JSONObject object = Data.getJSONObject(i);
				city = object.getString("�m������");
				add = object.getString("�g�a�Ϭq���m�Ϋت��Ϫ��P");
				date = object.getInt("�����~��");
				if( city.equals(args[1]) && add.contains(args[2]) && date > year)
				{
					total = total + object.getInt("�`����");
					cnt ++;
				}
			}
			if( cnt == 1) System.out.println(0);
			else System.out.println(total/cnt);
			*/
		}
}
