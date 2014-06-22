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
    private static String test() throws IOException
    {
    	System.out.println("START");
    	String inputLine;
    	StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        
        return a.toString();
    }
	private static int read(String Reg, String Add, String Year) throws IOException
	{
		 //System.out.println("START");
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
        			 //System.out.println(str.toString());
        			 if( cnt == 1)
        			 {
        				 if( str.toString().equals(Reg)) region[num] = str.toString();
        				 else objectMatch = 0;
        			 }
        			 else if( cnt == 3)
        			 {
        				 if( str.toString().contains(Add)) addr[num] = str.toString();
        				 else objectMatch = 0;
        			 }
        			 else if( cnt == 8)
        			 {
        				 if( str.toString().contains(Year)) year[num] = str.toString();
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
        	 //System.out.println(region[i]+" "+addr[i]+" "+year[i]+" "+value[i]);
        	 sum += value[i];
         }
         //System.out.println("END");
         return (int)sum/(num-1);
    }
	public static void main(String[] args) throws IOException, JSONException
	{
		url = args[0];
		data = new URL(url);
		connect = data.openConnection();
		in = new BufferedReader(new InputStreamReader(connect.getInputStream(), "UTF-8"));
		long StartTime = System.currentTimeMillis();
		System.out.println(read(args[1],args[2],args[3]));
		/*String tmp = test();
		//long StartTime = System.currentTimeMillis();
		JSONArray j = new JSONArray(tmp);
		int total = 0, cnt = 0;
		for(int i=0;i<j.length();i++)
		{
			JSONObject O = j.getJSONObject(i);
			//System.out.println(O.getString("鄉鎮市區")+O.getString("土地區段位置或建物區門牌")+O.getInt("交易年月"));
			if( O.getString("鄉鎮市區").equals(args[1]) )
			{
				total = total + O.getInt("總價元");
				cnt ++;
			}
		}*/
		long ProcessTime = System.currentTimeMillis() - StartTime;
		
		System.out.println(ProcessTime);
	}
}
