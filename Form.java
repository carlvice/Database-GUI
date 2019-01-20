import java.sql.*;
import javax.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Form extends JFrame implements ActionListener
{
	JTextField jtf[]=new JTextField[7];
	JLabel jl[]=new JLabel[7];
	JButton jb[]=new JButton[4];
	Connection con;
	Statement stmt;
	ResultSet rst;
	String qry="";
	int test;
	Form()
	{
		setVisible(true);
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER,250,28));
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e)
		{
		}
		
		for(int i=0;i<7;i++)
		{
			jl[i]=new JLabel();
			jtf[i]=new JTextField(15);
			add(jl[i]);
			add(jtf[i]);
			jtf[i].setEnabled(false);
		}
		jb[0]=new JButton("Add");jb[1]=new JButton("View");jb[2]=new JButton("Delete");jb[3]=new JButton("Modify");
		for(int i=0;i<4;i++)
		{
			add(jb[i]);
			jb[i].addActionListener(this);
		}
		jl[0].setText("Id");jl[1].setText("Name");jl[2].setText("Age");
		jl[3].setText("Branch");jl[4].setText("Email");jl[5].setText("Address");
		jl[6].setText("College");

	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand()=="Add")
		{
			for(int i=0;i<7;i++)
			{
				jtf[i].setEnabled(true);
			}
			jb[0].setText("Save");
			System.out.println("add");
			
		}
		if(ae.getActionCommand()=="Save")
		{
			test=JOptionPane.showConfirmDialog(this,"Are you sure ?","",JOptionPane.YES_NO_OPTION);
			if(test==0)
			{
				add();
			}
			else
			{
				for(int i=0;i<7;i++)
				{
					jtf[i].setText("");
				}
			}
			jb[0].setText("Add");
		
		}
		if(ae.getActionCommand()=="View")
		{
			new ViewB();
		}
		if(ae.getActionCommand()=="Delete")
		{
			new DelB();
		}
		if(ae.getActionCommand()=="Modify")
		{
			new ModB();
		}
	}
	void add()
	{
		String str[]=new String[7];
		for(int i=0;i<7;i++)
		{
			str[i]=jtf[i].getText();
		}	
		try
		{
			
			con=DriverManager.getConnection("jdbc:mysql://localhost/client","root","123");
			stmt=con.createStatement();
			System.out.println("add try");
			qry="insert into student values("+str[0]+",'"+str[1]+"',"+str[2]+",'"+str[3]+"','"+str[4]+"','"+str[5]+"','"+str[6]+"');";
			System.out.println(qry);
			stmt.executeUpdate(qry);
			System.out.println("query cmplt");
			stmt.close();
			con.close();
			
		}
		catch (Exception e)
		{

		}
		for(int i=0;i<7;i++)
		{
			jtf[i].setText("");
		}
		
	
	}
	class ViewB extends JFrame implements ActionListener
	{
		JLabel jl[]=new JLabel[7];
		JButton jb[]=new JButton [4];
		String qry="select * from student";
		ViewB()
		{
			setVisible(true);
			setSize(640,480);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLayout(new FlowLayout(FlowLayout.CENTER,359,20));
			jl[1]=new JLabel("Name");jl[2]=new JLabel("Age");jl[3]=new JLabel("Branch");
			jl[4]=new JLabel("Email");jl[5]=new JLabel("Address");jl[6]=new JLabel("College");
			jl[0]=new JLabel("Id");
			jb[0]=new JButton("|<");jb[1]=new JButton("<<");
			jb[2]=new JButton(">>");jb[3]=new JButton(">|");
			for(int i=0;i<7;i++)
			{
				add(jl[i]);
			}
			for(int i=0;i<4;i++)
			{
				add(jb[i]);
				jb[i].addActionListener(this);
			}
			try
			{
				con=DriverManager.getConnection("jdbc:mysql://localhost/client","root","123");
				stmt=con.createStatement();
				rst=stmt.executeQuery(qry);
				rst.first();
				jb[0].setEnabled(false);
				jb[1].setEnabled(false);
				jl[0].setText(rst.getString(1));
				jl[1].setText(rst.getString(2));
				jl[2].setText(rst.getString(3));
				jl[3].setText(rst.getString(4));
				jl[4].setText(rst.getString(5));
				jl[5].setText(rst.getString(6));
				jl[6].setText(rst.getString(7));
				
			}
			catch (Exception e)
			{
			
			}
			
			

		}
		public void actionPerformed(ActionEvent ae)
		{
			try
			{
				if(ae.getActionCommand()=="|<")
				{
					jb[0].setEnabled(false);
					jb[1].setEnabled(false);
					jb[2].setEnabled(true);
					jb[3].setEnabled(true);
					rst.first();
					jl[0].setText(rst.getString(1));
					jl[1].setText(rst.getString(2));
					jl[2].setText(rst.getString(3));
					jl[3].setText(rst.getString(4));
					jl[4].setText(rst.getString(5));
					jl[5].setText(rst.getString(6));
					jl[6].setText(rst.getString(7));

				}
				if(ae.getActionCommand()=="<<")
				{
					rst.previous();
					jb[0].setEnabled(true);
					jb[1].setEnabled(true);
					jb[2].setEnabled(true);
					jb[3].setEnabled(true);
					if(rst.isFirst())
					{
						jb[0].setEnabled(false);
						jb[1].setEnabled(false);
					}
					jl[0].setText(rst.getString(1));
					jl[1].setText(rst.getString(2));
					jl[2].setText(rst.getString(3));
					jl[3].setText(rst.getString(4));
					jl[4].setText(rst.getString(5));
					jl[5].setText(rst.getString(6));
					jl[6].setText(rst.getString(7));
					
				}
				if(ae.getActionCommand()==">>")
				{
					rst.next();
					jb[0].setEnabled(true);
					jb[1].setEnabled(true);
					if(rst.isLast())
					{
						jb[2].setEnabled(false);
						jb[3].setEnabled(false);
					}
					jl[0].setText(rst.getString(1));
					jl[1].setText(rst.getString(2));
					jl[2].setText(rst.getString(3));
					jl[3].setText(rst.getString(4));
					jl[4].setText(rst.getString(5));
					jl[5].setText(rst.getString(6));
					jl[6].setText(rst.getString(7));
				}
				if(ae.getActionCommand()==">|")
				{
					rst.last();
					jb[0].setEnabled(true);
					jb[1].setEnabled(true);
					jb[2].setEnabled(false);
					jb[3].setEnabled(false);
					jl[0].setText(rst.getString(1));
					jl[1].setText(rst.getString(2));
					jl[2].setText(rst.getString(3));
					jl[3].setText(rst.getString(4));
					jl[4].setText(rst.getString(5));
					jl[5].setText(rst.getString(6));
					jl[6].setText(rst.getString(7));
				}

			}
			catch (Exception e)
			{

			}
			
		}
	}
	class DelB extends JFrame implements ActionListener
	{
		JTextField jtf;
		JButton jb[]=new JButton[2];
		JLabel jl[]=new JLabel[7];
		Connection con;
		Statement stmt;
		ResultSet rst;
		DelB()
		{
			setVisible(true);
			setSize(640,480);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLayout(new FlowLayout(FlowLayout.CENTER,359,20));
			jl[0]=new JLabel("Id");
			add(jl[0]);
			jtf=new JTextField(15);
			add(jtf);
			jtf.setText("");
			jb[0]=new JButton("Search");
			add(jb[0]);
			for(int i=1;i<7;i++)
			{
				jl[i]=new JLabel();
				add(jl[i]);
			}
			jb[1]=new JButton("Delete");
			add(jb[1]);
			jb[0].addActionListener(this);jb[1].addActionListener(this);

			try
			{
				
				con=DriverManager.getConnection("jdbc:mysql://localhost/client","root","123");
				stmt=con.createStatement();


			}
			catch (Exception e)
			{

			}
		}
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand()=="Search")
			{
				try
				{
					if(jtf.getText().equals(""))
					{
						JOptionPane.showMessageDialog(this,"Enter a value to be searched");
					}
					else
					{
						String qry="select * from student where id ="+jtf.getText();
						System.out.println(qry);
						rst=stmt.executeQuery(qry);
						System.out.println("qry cmplt");
						if(rst.next())
						{
							jl[1].setText(rst.getString(2));
							jl[2].setText(rst.getString(3));
							jl[3].setText(rst.getString(4));
							jl[4].setText(rst.getString(5));
							jl[5].setText(rst.getString(6));
							jl[6].setText(rst.getString(7));
						}
						else
						{
							JOptionPane.showMessageDialog(this,"No Result Found");
							for(int i=1;i<7;i++)
							{
								jl[i].setText("");
							}
						}
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			if(ae.getActionCommand()=="Delete")
			{
				try
				{
					String qry="delete from student where id ="+jtf.getText()+";";
					System.out.println(qry);
					stmt.executeUpdate(qry);
					System.out.println("qry complt");
					JOptionPane.showMessageDialog(this,"Deleted Successfully!!");
					for(int i=1;i<7;i++)
					{
							jl[i].setText("");
					}
					jtf.setText("");

				
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	class ModB extends JFrame implements ActionListener
	{
		JTextField jtf[]=new JTextField[7];
		JButton jb[]=new JButton[2];
		JLabel jl[]=new JLabel[7];
		String str[]=new String[7];
		String nstr[]=new String[7];
		String qry="";
		Connection con;
		Statement stmt;
		ResultSet rst;
		ModB()
		{
			setVisible(true);
			setSize(640,480);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setLayout(new FlowLayout(FlowLayout.CENTER,359,20));
			jl[0]=new JLabel("Id");jl[1]=new JLabel("Name");jl[2]=new JLabel("Age");
			jl[3]=new JLabel("Branch");jl[4]=new JLabel("Email");jl[5]=new JLabel("Address");
			jl[6]=new JLabel("College");
			for(int i=0;i<7;i++)
			{
				jtf[i]=new JTextField(15);
				add(jl[i]);
				add(jtf[i]);
				jtf[i].addActionListener(this);
			}
			jb[0]=new JButton("Search");jb[1]=new JButton("Modify");
			add(jb[0]);add(jb[1]);
			jb[0].addActionListener(this);jb[1].addActionListener(this);
			try
			{
				
				con=DriverManager.getConnection("jdbc:mysql://localhost/client","root","123");
				stmt=con.createStatement();


			}
			catch (Exception e)
			{

			}
			

		}
		public void actionPerformed(ActionEvent ae)
		{
			if(ae.getActionCommand()=="Search")
			{
				try
				{
					if(jtf[0].getText().equals(""))
					{
						JOptionPane.showMessageDialog(this,"Enter a value to be searched");
					}
					else
					{
						String qry="select * from student where id ="+jtf[0].getText();
						System.out.println(qry);
						rst=stmt.executeQuery(qry);
						System.out.println("qry cmplt");
						if(rst.next())
						{
							jtf[1].setText(rst.getString(2));
							jtf[2].setText(rst.getString(3));
							jtf[3].setText(rst.getString(4));
							jtf[4].setText(rst.getString(5));
							jtf[5].setText(rst.getString(6));
							jtf[6].setText(rst.getString(7));
							for(int i=0;i<7;i++)
							{
								str[i]=jtf[i].getText();
							}
						}
						else
						{
							JOptionPane.showMessageDialog(this,"No Result Found");
							for(int i=0;i<7;i++)
							{
								jtf[i].setText("");
							}
						}
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				
			}
			if(ae.getActionCommand()=="Modify")
			{
				for(int i=0;i<7;i++)
				{
					nstr[i]=jtf[i].getText();
				}
				if(nstr[0].equals(str[0]))
				{				
				}
				else
				{
					
					try
					{
						qry="update student set id="+nstr[0]+" where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
					
				}
				if(nstr[1].equals(str[1]))
				{				
				}
				else
				{
					try
					{
						qry="update student set name='"+nstr[1]+"' where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
				}
				if(nstr[2].equals(str[2]))
				{				
				}
				else
				{
					try
					{
						qry="update student set age="+nstr[2]+" where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
					
				}
				if(nstr[3].equals(str[3]))
				{				
				}
				else
				{
					try
					{
						qry="update student set branch='"+nstr[3]+"' where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
				}
				if(nstr[4].equals(str[4]))
				{				
				}
				else
				{
					try
					{
						qry="update student set email='"+nstr[4]+"' where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
				}
				if(nstr[5].equals(str[5]))
				{				
				}
				else
				{
					try
					{
						qry="update student set address='"+nstr[5]+"' where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
				}
				if(nstr[6].equals(str[6]))
				{				
				}
				else
				{
					try
					{
						qry="update student set college='"+nstr[6]+"' where id="+str[0]+";";
						stmt.executeUpdate(qry);
					}
					catch (Exception e)
					{
					}
				}
			}

		}


	}
	public static void main(String[] args) 
	{
		new Form();
		
	}
}



		
