package org.dolphin;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.dolphin.game.Actor0;
import org.dolphin.game.Game;
import org.dolphin.game.api.exceptions.DestroyException;
import org.dolphin.game.api.exceptions.RoomChangedException;
import org.dolphin.game.api.types.AllOfObject;
import org.dolphin.parser.PlatformCore;
import org.dolphin.parser.gscriptLexer;
import org.dolphin.parser.gscriptParser;
import org.lateralgm.components.impl.ResNode;
import org.lateralgm.file.GmFile;
import org.lateralgm.file.GmFormatException;
import org.lateralgm.main.LGM;
import org.lateralgm.resources.Background;
import org.lateralgm.resources.GmObject;
import org.lateralgm.resources.ResourceReference;
import org.lateralgm.resources.Room;
import org.lateralgm.resources.Script;
import org.lateralgm.resources.Sprite;
import org.lateralgm.resources.Timeline;
import org.lateralgm.resources.Background.PBackground;
import org.lateralgm.resources.GmObject.PGmObject;
import org.lateralgm.resources.Path.PPath;
import org.lateralgm.resources.Room.PRoom;
import org.lateralgm.resources.Script.PScript;
import org.lateralgm.resources.Sound.PSound;
import org.lateralgm.resources.Sprite.PSprite;
import org.lateralgm.resources.sub.Action;
import org.lateralgm.resources.sub.Argument;
import org.lateralgm.resources.sub.BackgroundDef;
import org.lateralgm.resources.sub.Event;
import org.lateralgm.resources.sub.Instance;
import org.lateralgm.resources.sub.MainEvent;
import org.lateralgm.resources.sub.Tile;
import org.lateralgm.resources.sub.View;
import org.lateralgm.resources.sub.BackgroundDef.PBackgroundDef;
import org.lateralgm.resources.sub.Instance.PInstance;
import org.lateralgm.resources.sub.Tile.PTile;
import org.lateralgm.resources.sub.View.PView;

public class DolphinWriter {

    public static DolphinFrame df;
    public static GmFile gmFile;
    public static String FileFolder,  filename,  projectfolder;
    public static boolean standalone=false;
    File location;
    PlatformCore pc = new PlatformCore();

    public DolphinWriter(){}
    
    public static void writeEclipseProjectFiles(String location,String name) {
    	FileWriter projectFileFW;
		try {
			projectFileFW = new FileWriter(location + ".classpath");
			BufferedWriter projectFile = new BufferedWriter(projectFileFW);
	        print(projectFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        print(projectFile, "<classpath>");
	        print(projectFile, "	<classpathentry kind=\"src\" path=\"src\"/>");
	        print(projectFile, "	<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>");
	        print(projectFile, "	<classpathentry kind=\"output\" path=\"bin\"/>");
	        print(projectFile, "</classpath>");
	        projectFile.close();
	        
	        projectFileFW = new FileWriter(location + ".project");
			projectFile = new BufferedWriter(projectFileFW);
			print(projectFile, "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			print(projectFile, "<projectDescription>");
			print(projectFile, "	<name>"+name+"</name>");
			print(projectFile, "	<comment></comment>");
			print(projectFile, "	<projects>");
			print(projectFile, "	</projects>");
			print(projectFile, "	<buildSpec>");
			print(projectFile, "		<buildCommand>");
			print(projectFile, "			<name>org.eclipse.jdt.core.javabuilder</name>");
			print(projectFile, "			<arguments>");
			print(projectFile, "			</arguments>");
			print(projectFile, "		</buildCommand>");
			print(projectFile, "	</buildSpec>");
			print(projectFile, "	<natures>");
			print(projectFile, "		<nature>org.eclipse.jdt.core.javanature</nature>");
			print(projectFile, "	</natures>");
			print(projectFile, "	<linkedResources>");
			print(projectFile, "		<link>");
			print(projectFile, "		</link>");
			print(projectFile, "	</linkedResources>");
			print(projectFile, "</projectDescription>");
			projectFile.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    public DolphinWriter(DolphinFrame df, GmFile gmFile, File file) {
    	System.out.println("gmFile"+gmFile);
    	if (gmFile.filename == null){filename="unnamedgame";}
    	else
        filename = gmFile.filename.substring(gmFile.filename.lastIndexOf(File.separator) + 1);
        df.progress(1, "Starting to parse files", "Starting Dolphin for " + filename);
        this.df = df;
        this.gmFile = gmFile;
        this.location = file;
        
        this.FileFolder = file.getPath() + File.separator + "Dolphin Projects" + File.separator + filename + File.separator + "src"+ File.separator + "org" + File.separator + "dolphin" + File.separator + "game" + File.separator;
        projectfolder = file.getPath() + File.separator + "Dolphin Projects" + File.separator + filename + File.separator+ "src"+ File.separator;
        new File(FileFolder).mkdirs();
        createFolders();
        writeEclipseProjectFiles(file.getPath() + File.separator + "Dolphin Projects" + File.separator + filename + File.separator,filename);
        
        LGM.commitAll();//make sure all LGM resources are saved
        try {
            df.progress(10, "Writing Game.java", "Writing Game.java and extracting sprites ");
            writeGameJava();
            df.progress(20, "Extracting backgrounds", "Extracting backgrounds");
            parseBackgrounds();
            df.progress(30, "Writing scripts", "Writing scripts");
            parseScripts();
            df.progress(40, "Writing timelines", "Writing timelines");
            parseTimelines();
             df.progress(50, "Exporting game information", "Exporting game information");
            exportGameinfo();
            df.progress(70, "Writing object code", "Writing object code");
            parseObjects();
            df.progress(80, "Writing room code", "Writing room code");
            parseRooms();
            df.progress(80, "Writing Game settings","Writing Game settings");
            parseSettings();
            df.progress(90, "Compiling java files", "Compiling java files");
            DolphinCompiler compiler = new DolphinCompiler();
        } catch (Exception e) {
            showException(e);
            e.printStackTrace();
        }
    }


    /*
     * Copy the runner folders to the project folder
     */
    public void createFolders() {
        try {
            pc.copyDirectory(new File(System.getProperty("user.dir") + File.separator + "plugins" + File.separator + "runner"), new File(projectfolder));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void parsePaths(BufferedWriter game) throws IOException {
    	String paths="";
    	for (org.lateralgm.resources.Path p : gmFile.paths) {
    		paths+=p.getName()+", ";
    	}
    	print(game,"public static Path "+paths+" DOLPHIN_nullpath;");
    	print(game, "  public static void initPaths(){");
    for (org.lateralgm.resources.Path p : gmFile.paths) {
    	
    	if (p!=null){
            	print(game,"     "+ p.getName() + " = new Path(" + p.get(PPath.CLOSED) + ", " + p.get(PPath.SMOOTH) + ", " + p.get(PPath.PRECISION) + ");");
            for (int i = 0; i < p.points.size(); i++) {
            	print(game,"     "+p.getName()+".addPoint("+p.points.get(i).getX()+","+p.points.get(i).getY()+","+p.points.get(i).getSpeed()+");");
			}
            if (p.get(PPath.CLOSED))
            print(game,"     "+p.getName()+".setClosed("+p.get(PPath.CLOSED)+");");
            print(game,"     "+p.getName()+".findDistance();");
        }
    }
    
    print(game, "  }");
    }
    
    public void writeinitRooms(BufferedWriter game) throws IOException {
        print(game, "  public static void initRooms(){");
        print(game, "     rooms=new Vector<Room2D>();");
        int i = 0;
        
        Enumeration e = LGM.root.getChildAt(8).children();
			while (e.hasMoreElements()){
				ResNode rn = (ResNode)e.nextElement();
				if (rn.getRes()!=null)
				print(game, "     rooms.add(new " + rn.getRes().get().getName() + "(" + i + "));");
	            i++;
			}
		
		/*for (Room o : gmFile.rooms) {
            print(game, "     rooms.add(new " + o.getName() + "(" + i + "));");
            i++;
        }*/
        print(game, "    currentRoom=rooms.firstElement();");
        print(game, "    currentRoom.setvisible();");
        print(game, "  }");
    }

    public void writeGameJava() throws IOException {
        FileWriter gameFW = new FileWriter(FileFolder + "Game.java");
        BufferedWriter game = new BufferedWriter(gameFW);

        print(game, "package org.dolphin.game;");
        print(game, "import java.awt.Dimension;");
        print(game, "import java.io.PrintWriter;");
        print(game, "import java.io.StringWriter;");
        print(game, "import java.io.Writer;");
        print(game, "import java.util.Vector;");
        
        print(game, "import javax.swing.JOptionPane;");

        print(game, "import org.dolphin.game.api.Clipboard;");
        print(game, "import org.dolphin.game.api.components.Room2D;");
        print(game, "import com.golden.gamedev.GameLoader;");
        print(game, "import com.golden.gamedev.engine.graphics.WindowedMode;");
        print(game, "import java.awt.image.BufferedImage;");
        print(game, "import org.dolphin.game.api.components.*;");
        print(game,"import org.dolphin.game.api.types.AllOfObject;");

        print(game, "public class Game extends org.dolphin.game.api.gtge.BasicGame {");

        print(game, "public static org.dolphin.game.Game thegame;//used to get this game object");
        String actor_names="";
        for (GmObject a : gmFile.gmObjects) {
            /*check for duplicate objects*/
        	actor_names += a.getName()+"= new AllOfObject(new "+a.getName()+"()), ";
        }
        
        print(game,"public static AllOfObject "+actor_names+" nullallofobject;");
        
        
        print(game, "");
        print(game, "public static void setupGame() {");
        print(game, "	game = new GameLoader();");
        print(game, "        thegame=new Game();");
        print(game, "	game.setup(thegame, new Dimension(640, 480), false);");
        print(game, "	frame = ((WindowedMode) Game.game.getGame().bsGraphics).getFrame();");
        print(game, "   initPaths();");
        print(game, "}");
        print(game, "");
        print(game, "public BufferedImage loadBackground(String name){");
        print(game, "if (!backgrounds.containsKey(name))");
        print(game, "{");
        print(game, "    backgrounds.put(name, getImage(name+\".png\"));");
        print(game, "}");
        print(game, "return (BufferedImage)backgrounds.get(name);");
        print(game, "}");
        print(game, "");
        print(game, "public Sprite loadSprite(String name){");
        print(game, "  if (!sprites.containsKey(name))");
        print(game, "{");
        print(game, "    sprites.put(name, getSprite(name));");
        print(game, "}");
        print(game, "  return (Sprite)sprites.get(name);");
        print(game, "}");
        print(game, "public Sound loadSound(String name){");
        print(game, "  if (!sounds.containsKey(name))");
        print(game, "{");
        print(game, "    sounds.put(name, getSound(name));");
        print(game, "}");
        print(game, "  return (Sound)sounds.get(name);");
        print(game, "}");


        parseSprites(game);
        parseSounds(game);
        parsePaths(game);
        writeinitRooms(game);

        print(game, "        public void initResources() {");
        print(game, "		super.initResources();");
        print(game, "		initRooms();");
        print(game, "	}");
        print(game, "");
        print(game, "	public static void main(java.lang.String[] args) {");
        print(game, "		parameter_count = args.length;");
        print(game, "		parameters = args;");
        print(game, "		gameType = \"Application\";");
        print(game, "		try {");
        print(game, "			setupGame();");
        print(game, "			game.start();");
        print(game, "		} catch (Exception e) {");
        print(game, "			/*");
        print(game, "			 * Display any Exceptions that occur during the game");
        print(game, "			 */");
        print(game, "			final Writer result = new StringWriter();");
        print(game, "			final PrintWriter printWriter = new PrintWriter(result);");
        print(game, "			e.printStackTrace(printWriter);");
        print(game, "			Clipboard.setText((\"\" + result.toString() + Clipboard.getText()));");
        print(game, "			JOptionPane");
        print(game, "					.showMessageDialog(null,\"Error: \"");
        print(game, "									+ e");
        print(game, "									+ \", \"");
        print(game, "									+ e.getMessage()");
        print(game, "									+ \". \\n Stack trace:\"");
        print(game, "									+ result.toString()");
        print(game, "									+ \"\\n \\n The Error has been added to clipboard, just before the previous contents of the clipboard. \\n Please contact the G-Java development team for help. http://forums.g-java.com\");");
        print(game, "			System.out.println(\"Exception:\"+result.toString());");
        print(game, "			System.exit(1); // Exit the game");
        print(game, "		}");
        print(game, "");
        print(game, "	}");

        print(game, "}");//end class
        game.close();
    }
    
    void parseSettings(){
    	try {
    	FileWriter settingsFW = new FileWriter(FileFolder + "GameSettings.java");
        BufferedWriter settings = new BufferedWriter(settingsFW);

        print(settings, "package org.dolphin.game;");
        print(settings, "");
        print(settings, "import java.awt.Color;");
        print(settings, "");
        print(settings, "public class GameSettings {");
        print(settings, "");
        print(settings, "	public static Color Gameinfoback = Color.BLUE;");
        print(settings, "");
        print(settings, "}");
        settings.close();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
    	

    void parseSounds(BufferedWriter game) throws IOException {
        df.progress(40, "Extracting Sounds", "Extracting Sounds");
        print(game, "  public Sound getSound(String name){");
        String theelse = "   ";
        for (org.lateralgm.resources.Sound s : gmFile.sounds) {
        	s.get(PSound.FILE_TYPE);
            FileOutputStream f = new FileOutputStream(FileFolder + File.separator + s.getName() + s.get(PSound.FILE_TYPE));
            f.write(s.data);
            f.close();

            print(game, theelse + " if (name.equals(\"" + s.getName() + "\")) return new Sound(\"" + s.getName() + s.get(PSound.FILE_TYPE) + "\");");
            theelse = "   else";

        }
        print(game, " return null;");
        print(game, "  }");

    }
    
    
    void parseBackgrounds() throws IOException {
        for (org.lateralgm.resources.Background b : gmFile.backgrounds) {
        	if(b.getBackgroundImage()!=null)
            ImageIO.write(b.getBackgroundImage(), "png", new File(FileFolder + File.separator + b.getName() + ".png"));

        }
    }

    void parseSprites(BufferedWriter game) throws IOException {
        print(game, "  public Sprite getSprite(String name){");
        String theelse = "   ";
        for (org.lateralgm.resources.Sprite s : gmFile.sprites) {
        	if (s!=null){
            String subimg = "";
            for (int i = 0; i < s.subImages.size(); i++) {
                BufferedImage img = s.subImages.get(i);
                if(img!=null){
                	int tpixel = img.getRGB(img.getMinX(),img.getHeight() - 1);
                	img = Transparency.makeColorTransparent(img,new Color(tpixel));
                ImageIO.write(img, "png", new File(FileFolder + File.separator + s.getName() + "[" + i + "].png"));
                subimg += ",getImage(\"" + s.getName() + "[" + i + "].png" + "\")";
                }
            }
            s.get(PSprite.BB_BOTTOM);
            if (s.getDisplayImage() !=null){
            print(game, theelse + " if (name.equals(\"" + s.getName() + "\")) return new Sprite(\"" + s.getName() + "\"," + s.getDisplayImage().getHeight() + ", " + s.getDisplayImage().getWidth() + ", " +  s.get(PSprite.BB_LEFT) + ", " + s.get(PSprite.BB_RIGHT) + ", " + s.get(PSprite.BB_BOTTOM) + ", " + s.get(PSprite.BB_TOP) + ", " + s.get(PSprite.ORIGIN_X) + ", " + s.get(PSprite.ORIGIN_Y) + ", " + s.get(PSprite.TRANSPARENT) + ", new BufferedImage[]{" + subimg.substring(1) + "});");
            } else {
            	//the sprite does not have an image
            	print(game, theelse + " if (name.equals(\"" + s.getName() + "\")) return new Sprite(\"" + s.getName() + "\",0, 0, 0, 0, 0, 0, " + s.get(PSprite.ORIGIN_X) + ", " + s.get(PSprite.ORIGIN_Y) + ", " + s.get(PSprite.TRANSPARENT) + ", new BufferedImage[]{});");

            }
            theelse = "   else";

        }
        }
        print(game, " return null;");
        print(game, "  }");
        }
    

    void parseScripts() throws GmFormatException {
        ArrayList<String> names = new ArrayList<String>(gmFile.gmObjects.size());

        pc.inscript=true;

        try {
            FileWriter scriptFW = new FileWriter(FileFolder + "Scripts.java");
            BufferedWriter script = new BufferedWriter(scriptFW);

            print(script, "package org.dolphin.game;");

            print(script, "import org.dolphin.game.api.GCL_Actions;");
            print(script, "import org.dolphin.game.api.types.Variable;");
            print(script, "import org.dolphin.game.api.types.*;");
            print(script, "import org.dolphin.game.api.components.*;");
            print(script, "public class Scripts extends GCL_Actions {");

            for (Script s : gmFile.scripts) {
                /*check for duplicate scripts*/
                String name = s.getName();
                name = PlatformCore.fixName(name);
                s.setName(name);
                pc.current = name;
                if (names.contains(name)) {
                    throw new GmFormatException(gmFile, "Duplicate object name: " + name);
                }
                print(script, "public Object " + name + "(Object... arguments){");
                
                print(script, "" + this.parseGCL(""+s.get(PScript.CODE)));
                print(script, "return false;");
                print(script, "}");
            }
            print(script, "}");//end scripts class
            script.close();
        } catch (Exception e) {
            showException(e);
        }

        pc.inscript=false;
    }

    void exportGameinfo(){
        //gameinformation.rtf
        FileWriter giFW = null;
        try {
            giFW = new FileWriter(FileFolder + "gameinformation.rtf");
            BufferedWriter gi = new BufferedWriter(giFW);
            print(gi, gmFile.gameInfo.gameInfoStr);
            gi.close();
        } catch (IOException ex) {
            Logger.getLogger(DolphinWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                giFW.close();
            } catch (IOException ex) {
                Logger.getLogger(DolphinWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void parseTimelines() throws GmFormatException {

        ArrayList<String> objectnames = new ArrayList<String>(gmFile.gmObjects.size());

        for (Timeline t : gmFile.timelines) {
            /*check for duplicate objects*/
            String name = t.getName();
            pc.current = name;
            if (objectnames.contains(name)) {
                throw new GmFormatException(gmFile, "Duplicate object name: " + name);
            }
            try {
                FileWriter tlFW = new FileWriter(FileFolder + name + ".java");
                BufferedWriter tl = new BufferedWriter(tlFW);
                print(tl, "package org.dolphin.game;");

                print(tl, "import org.dolphin.game.api.components.Timeline;");
                
                print(tl, "import org.dolphin.game.api.types.*;");
                print(tl, "public class "+name+" extends Timeline {");
                print(tl, "public void checksteps(double step){");
                for (int i = 0; i < t.moments.size(); i++) {
                    
                    print(tl, "if (step=="+t.moments.get(i).stepNo+"){");
                    Event ev = new Event();
                    ev.actions = t.moments.get(i).actions;
                print(tl, ""+parseGCL(getActionsCode(ev)));
                print(tl, "}");
                }

                print(tl, "}");
                print(tl, "}");//end the class
                tl.close();
            } catch (Exception e) {
                System.out.println("Exception while parsing a timeline" + e.getMessage());

                showException(e);
            }

        }

    }

    void parseObjects() throws GmFormatException {
        ArrayList<String> objectnames = new ArrayList<String>(gmFile.gmObjects.size());

        pc.inscript=false;//make sure it doesn't think it is a script
        
        for (GmObject a : gmFile.gmObjects) {
            /*check for duplicate objects*/
            String name = a.getName();
            name = PlatformCore.fixName(name);
            a.setName(name);
            pc.current = name;
            if (objectnames.contains(name)) {
                throw new GmFormatException(gmFile, "Duplicate object name: " + name);
            }
            try {
                FileWriter actorFW = new FileWriter(FileFolder + name + ".java");
                BufferedWriter actor = new BufferedWriter(actorFW);

                print(actor, "package org.dolphin.game;");
                print(actor,"import java.awt.Graphics;");
                print(actor, "import org.dolphin.game.api.components.Actor;");
                print(actor, "import org.dolphin.game.api.exceptions.DestroyException;");
                print(actor, "import org.dolphin.game.api.types.*;");
                print(actor, "import org.dolphin.game.api.exceptions.RoomChangedException;");
                print(actor, "import java.awt.Graphics2D;");
                print(actor, "");
                print(actor, "public class " + name + " extends Actor {");
                print(actor, "");
                print(actor, " public "+name+"(){}");
                print(actor, "");
                print(actor, " public   " + name + "(double X,double Y,double instance_id) {");
                
                ResourceReference<Sprite> sprite = a.get(PGmObject.SPRITE);
                if (sprite == null) {
                    print(actor, "        super(\"" + a.getName() + "\", null, " + a.get(PGmObject.SOLID) + ", " + a.get(PGmObject.VISIBLE) + ", " + a.get(PGmObject.DEPTH) + ", " + a.get(PGmObject.PERSISTENT) + ");");
                } else {
                    print(actor, "        super(\"" + a.getName() + "\", Game.thegame.loadSprite(\"" + sprite.get().getName() + "\")," + a.get(PGmObject.SOLID) + ", " + a.get(PGmObject.VISIBLE) + ", " + a.get(PGmObject.DEPTH) + ".0 , " + a.get(PGmObject.PERSISTENT) + ");");
                }
                print(actor, "        xstart = X;xprevious=X;yprevious=Y;");
                print(actor, "        ystart = Y;");
                print(actor, "        x = X;");
                print(actor, "        y = Y;");
                print(actor, "        setObject_index(Game."+name+");");
                print(actor, "        this.instance_id = instance_id;");
                print(actor, "        self=this;");
                /*print(actor, "        try{");
                		print(actor, "            Create(); //needs to be called after x and y are set up");
                				print(actor, "            } catch(RoomChangedException e){");
                						print(actor, "             }");*/
                print(actor, "    }");
                String callevents="";
                for (int j = 0; j < 11; j++) {
                	try{
                    /*
                     * Create Event
                     */
                    if (j == 0) {
                        for (Event ev : a.mainEvents.get(j).events) {
                            writeCreateEvent(actor, ev);
                        }
                    } /*
                     * Destroy Event
                     */ else if (j == 1) {
                        for (Event ev : a.mainEvents.get(j).events) {
                            writeDestroyEvent(actor, ev);
                        }
                    } /*
                     * Alarm Event
                     */ else if (j == 2 && a.mainEvents.get(j).events.size()>0) {
                    	 callevents+="Alarm();";
                        print(actor, "   public void performAlarm(int alarmid) throws RoomChangedException {");
                        for (Event ev : a.mainEvents.get(j).events) {
                            writeAlarmEvent(actor, ev);
                        }
                        
                        print(actor, "    }");
                    } /*
                     * Step Event
                     */ else if (j == 3) {
                    	 
                        for (Event ev : a.mainEvents.get(j).events) {
                            
                            if (ev.id == 1) {
                            	pc.event = "Begin Event";
                            	callevents+="BeginStep();";
                                print(actor, "   public void BeginStep() throws DestroyException,RoomChangedException {");
                            } else if (ev.id == 0) {
                            	pc.event = "Step Event";
                            	callevents+="Step();";
                                print(actor, "   public void Step() throws DestroyException,RoomChangedException {");
                            } else if (ev.id == 2) {
                            	pc.event = "End Event";
                            	callevents+="EndStep();";
                                print(actor, "   public void EndStep() throws DestroyException,RoomChangedException {");
                            }
                            print(actor, "   " + parseGCL(getActionsCode(ev)));
                            
                            print(actor, "    }");
                        }
                    } /*
                     * Collision Event
                     */ else if (j == 4 && a.mainEvents.get(j).events.size()>0) {
                    	 pc.event = "Collision Event";
                    	 callevents+="checkCollision();";
                    	 print(actor, "public void callCollision(){");
                    			 print(actor, "try{checkCollision();}catch(Exception e){}");
                    					 print(actor, "    }");
                        print(actor, "   public void Collision(java.lang.String name) throws RoomChangedException{");
                        for (Event ev : a.mainEvents.get(j).events) {
                            //System.out.println("ev.id" + ev.id);
                            print(actor, "   if(name.equals(\"" + ev.other.get().getName() + "\")){");
                            print(actor, "   " + parseGCL(getActionsCode(ev)));
                            print(actor, "}");
                        }
                        
                        print(actor, "}");
                    } /*
                     * Keyboard Event
                     */ else if (j == 5 && a.mainEvents.get(j).events.size()>0) {
                    	 pc.event = "Keyboard Event";
                    	 callevents+="Keyboard();";
                        print(actor, "   public void Keyboard() throws RoomChangedException {");
//
                        for (Event ev : a.mainEvents.get(j).events) {
                        	pc.event = "Keyboard"+Event.getGmKeyName(ev.id)+" Event";
                            
                            print(actor, "if (Game.game.getGame().keyDown(" + ev.id + ")){");
                            print(actor, "   " + parseGCL(getActionsCode(ev)));
                            print(actor, "}");
                        }
                        
                        print(actor, "}");
                    } /*
                     * Mouse Event
                     */ else if (j == 6 && a.mainEvents.get(j).events.size()>0) {
                    	 pc.event = "Mouse Event";
                        for (Event ev : a.mainEvents.get(j).events) {
                        	pc.event = "Mouse "+ev.id+" Event";
                            System.out.println("Mouse event id:" + ev.id);
                            print(actor, "//mouse event id:" + ev.id);
                        }
                    } 
                    /*
                     * Other Event
                     */ else if (j == 7) {
                    	 pc.event = "Other Event";
                        for (Event ev : a.mainEvents.get(j).events) {
                            System.out.println("Other event id:" + ev.id);
                            print(actor, "//other event:" + ev.id);
                            if (ev.id == 0){
                            	pc.event = "Outside Room Event";
                            	callevents+="OutsideRoom();";
                                print(actor, "   public void OutsideRoom() throws DestroyException,RoomChangedException {");
                                print(actor,"  if (x<0 || x>Game.thegame.currentRoom.width || y<0 || y>Game.thegame.currentRoom.height) {");
                            }
                            else if (ev.id == 7) {
                            	pc.event = "Animation End Event"; //called in actor draw event
                            	//callevents+="EndOfAnimation();";
                                print(actor, "   public void EndOfAnimation() throws DestroyException,RoomChangedException {");
                            } else {
                            	pc.event = "Other Event"+ev.id;
                            	//callevents+="EndOfAnimation();";
                                print(actor, "   public void OtherEvent"+ev.id+"() throws DestroyException,RoomChangedException {");
                    
                            }
                            print(actor, "   " + parseGCL(getActionsCode(ev)));
                            print(actor, "    }");
                            if (ev.id == 0){
                            print(actor, "    }");}
                        }
                    } /*
                     * Draw Event
                     */ else if (j == 8) {
                    	 if (a.mainEvents.get(j).events.size()>0){
                    	 pc.event = "Draw Event";
                        print(actor, "    public void Draw_event(Graphics g) throws RoomChangedException{");
                        for (Event ev : a.mainEvents.get(j).events) {
                            System.out.println("ev.id" + ev.id);
                            print(actor, "   " + parseGCL(getActionsCode(ev)));
                        }
                        
                        print(actor, "     }");
                    	 }
                    } /*
                     * Key press Event
                     */ else if (j == 9) {
                    	 String events="";
                    	 pc.event = "Key press Event";
                    	 int i=0;
                        for (Event ev : a.mainEvents.get(j).events) {
                        	if (i!=0) events+=" else ";
                        	String evname = "_"+Event.getGmKeyName(ev.id).replaceAll(" ", "_");
                        	pc.event = "Key press "+evname+" Event";
                        	events+="if (keycode==" + ev.id + "){"+evname+"KeyPressed(keycode);}\n";
                        	print(actor, "   public void "+evname+"KeyPressed(int keycode) throws DestroyException, RoomChangedException {");
                            print(actor, "     if (keycode==" + ev.id + "){");
                            print(actor, "        " + parseGCL(getActionsCode(ev)));
                            print(actor, "     }");
                            print(actor, "     }");
                            i++;
                        }
                        if (!events.equals("")){
                            print(actor, "   public void KeyPressed(int keycode) throws DestroyException, RoomChangedException {");
                            print(actor,events);
                            print(actor, "   }");
                            }
                    } 
                    /*
                     * Key release Event
                     */ else if (j == 10) {
                    	 pc.event = "Key release Event";
                    	 String events=""; 
                        for (Event ev : a.mainEvents.get(j).events) {
                        	pc.event = "Key release "+Event.getGmKeyName(ev.id)+" Event";
                        	events+="_"+Event.getGmKeyName(ev.id)+"KeyReleased(keycode);";
                        	print(actor, "   public void _"+Event.getGmKeyName(ev.id)+"KeyReleased(int keycode) throws DestroyException, RoomChangedException {");
                            print(actor, "     if (keycode==" + ev.id + "){");
                            print(actor, "        " + parseGCL(getActionsCode(ev)));
                            print(actor, "     }"); 
                            print(actor, "     }");
                        }
                        if (!events.equals("")){
                        print(actor, "   public void KeyReleased(int keycode) throws DestroyException, RoomChangedException {");
                        print(actor,events);
                        print(actor, "   }");
                        }
                    } else {
                        for (Event ev : a.mainEvents.get(j).events) {


                            System.out.println("unknown event" + ev.id + " " + getActionsCode(ev));
                        }
                    }
                    
                    
                    
                	}catch(Exception e){
                		e.printStackTrace();
                		df.ta.append(e.getMessage()+"\n"+e.getStackTrace().toString());
                	}
                }
                /*
                 * Now write the call events method                  
                 */
                print(actor,"public void callEvents() throws RoomChangedException {");
                print(actor,"try{");
                print(actor,""+callevents+" Move();");//used to add Move
                print(actor,"} catch (Exception d) {} //DestoryException etc");
                print(actor,"}");
                print(actor, "");
                print(actor, "}");//end the class
                actor.close();
            } catch (Exception e) {
                System.out.println("Exception while parsing an object/actor" + e.getMessage());

                showException(e);
            }

        }
    }

    public void writeCreateEvent(BufferedWriter actor, Event ev) throws IOException {
        pc.event = "Create Event";
        print(actor, "  public void Create() throws RoomChangedException {");
        print(actor, " " + parseGCL(getActionsCode(ev)));
        print(actor, " }");
    }

    public void writeDestroyEvent(BufferedWriter actor, Event ev) throws IOException {
        pc.event = "Destroy Event";
        print(actor, "  public void Destroy() throws RoomChangedException {");
        print(actor, " " + parseGCL(getActionsCode(ev)));
        print(actor, " }");
    }

    public void writeAlarmEvent(BufferedWriter actor, Event ev) throws IOException {
        pc.event = "Alarm" + ev.id + " Event";
        print(actor, "  if (alarmid==" + ev.id + ") {");
        print(actor, " " + parseGCL(getActionsCode(ev)));
        print(actor, " }");
    }

    public String parseGCL(String code) throws IOException {
        //change code simply for testing
        gscriptParser parser;
        gscriptLexer lex = null;

        //code = "int i; int ii; int iii; { me = 3; if (5==2) {}} /* hey */  return 8;";
        //System.out.println("CODE:"+code);
        FileWriter ftempcode = new FileWriter("tempcode.gcl");
        BufferedWriter tempcode = new BufferedWriter(ftempcode);
        tempcode.write(code);
        tempcode.close();
        
        lex = new gscriptLexer(new ANTLRFileStream(new File("tempcode.gcl").getAbsolutePath()));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        try {
            parser = new gscriptParser(tokens);
            //parser.DEFAULT_TOKEN_CHANNEL=80;

            parser.setPlatform(pc);
            pc.localVariables = new Vector(); //reset local variables

            parser.code();

        //System.out.println("Finished! Code output:" + pc.returncode);
        } catch (Exception e) {
            System.out.println("Error with parser:" + e + e.getLocalizedMessage() + " " + e.getMessage() + "\n code:" + code);
        e.printStackTrace();
        }
        
        
        
        return pc.returncode;
    }


    /*credits to enigma team for this code*/
    public String getActionsCode(Event ev) {
        String code = "";
        for (Action act : ev.actions) {
            code += System.getProperty("line.separator");
            
            if (act.getLibAction().actionKind == Action.ACT_CODE) {
                code += "{\n" + act.getArguments().get(0).getVal() + "\n}";
            } else if (act.getLibAction().actionKind == Action.ACT_BEGIN) {
                code += "{";
            } else if (act.getLibAction().actionKind == Action.ACT_END) {
                code += "}";
            } else if (act.getLibAction().actionKind == Action.ACT_ELSE) {
                code += " else ";
            } else if (act.getLibAction().actionKind == Action.ACT_VARIABLE) {
                if (act.isRelative()) {
                    code += "" + act.getArguments().get(0).getVal() + " += (" + act.getArguments().get(1).getVal() + ");";
                } else {
                    code += "" + act.getArguments().get(0).getVal() + " = (" + act.getArguments().get(1).getVal() + ");";
                }
            /*}else if (act.getLibAction()..actionKind == 0){
            code+="\n";*/
            
            } else if (act.getLibAction().actionKind == Action.ACT_EXIT) {
                code += "return;";
            }
            else if (act.getLibAction().actionKind == Action.ACT_REPEAT) {
                code += "repeat(" + act.getArguments().get(0).getVal() + ")";
            } else if  (act.getLibAction().execType == Action.EXEC_NONE){
            	//System.out.println("comment:" );
            	code="//action_comment";
            } else {
            	String instance="";
                if (act.getLibAction().question) {
                    //System.out.println("question:" + act.getLibAction().execInfo);
                    code += "if (";
                    if (act.isNot()) {
                        code += "!";
                    }
                }
                
                else {
                    code += "{\n";
                    //code += "with("+act.getAppliesTo().get().getName()+") {\n";
                    ResourceReference<GmObject> at = act.getAppliesTo();
        			if (at != null)
        				{
        				if (at == GmObject.OBJECT_OTHER){
        					instance="other.";	
        				}
        				else if (at == GmObject.OBJECT_SELF){
        					instance="";	
        				}
        				else
        					//System.out.println("action with: dosomething else");
        				instance=at.get().getName()+".";
        				}
                    
                    if (act.isRelative()) {
                        code += "argument_relative=" + act.isRelative() + "; ";
                    }
                }
                code += instance+act.getLibAction().execInfo + "(";

                for (int i = 0; i < act.getArguments().size(); i++) {
                    Argument arg = act.getArguments().get(i);
                    if (i != 0) {
                        code += ", ";
                    }
                    if (arg.kind == arg.ARG_STRING) {
                        code += "\"" + arg.getVal() + "\"";
                    } else if (arg.kind == arg.ARG_BOTH) {
                        code += "\"" + arg.getVal() + "\"";
                    } else if (arg.kind == arg.ARG_FONT && arg.getRes() != null) {
                        code += "\"" + arg.getRes().get().getName() + "\"";
                    } else if (arg.kind == arg.ARG_GMOBJECT && arg.getRes() != null) {
                        code += "" + arg.getRes().get().getName() + "";
                    } else if (arg.kind == arg.ARG_PATH && arg.getRes() != null) {
                        code += "" + arg.getRes().get().getName() + "";
                    } else if (arg.kind == arg.ARG_ROOM && arg.getRes() != null) {
                        code += arg.getRes().get().getName();
                    } else if (arg.kind == arg.ARG_SCRIPT && arg.getRes() != null) {
                        code += "\"" + arg.getRes().get().getName() + "\"";
                    } else if (arg.kind == arg.ARG_SOUND && arg.getRes() != null) {
                        code += "\"" + arg.getRes().get().getName() + "\"";
                    } else if (arg.kind == arg.ARG_SPRITE && arg.getRes() != null) {
                        code += "\"" + arg.getRes().get().getName() + "\"";
                    } else if (arg.kind == arg.ARG_TIMELINE && arg.getRes() != null) {
                        code += arg.getRes().get().getName();
                    } else if (arg.kind == arg.ARG_COLOR) {
                        code += "\"" + arg.getVal() + "\"";
                    } else {
                    	if(arg.getVal().equals("")){
                    		arg.setVal("0");
                    		
                    	}
                        code += arg.getVal();
                    }
                }

                if (act.getLibAction().question) {
                    if (act.isRelative()) {
                    code+=",true";
                    }
                    code += "))";
                } else {

                    code += ");";
                    if (act.isRelative()) {
                        code += "argument_relative=false; ";
                    }
                    code += "\n}";
                }

            }
            
        }
        //System.out.println("code:" + code);
        return code;
    }

    void showException(Exception e) {
        e.printStackTrace();
        df.progress(0, "Exception failed to convert!", "Error:" + e.getMessage() + "" + e.getStackTrace());
    }

    void parseRooms() {
        // out.write4(f.rooms.size());
        for (Room r : gmFile.rooms) {
        	/*check for duplicate objects*/
            String name = r.getName();
            name = PlatformCore.fixName(name);
            r.setName(name);
            pc.current = name;
            /*if (objectnames.contains(name)) {
                throw new GmFormatException(gmFile, "Duplicate object name: " + name);
            }*/
            try {
                FileWriter sceneFW = new FileWriter(FileFolder + name + ".java");
                BufferedWriter scene = new BufferedWriter(sceneFW);

                print(scene, "package org.dolphin.game;");
                print(scene, "");

                print(scene, "import java.awt.Color;");
                print(scene, "");
                print(scene, "import org.dolphin.game.api.*;");

                print(scene, "import org.dolphin.game.api.components.Background;");
                print(scene, "import org.dolphin.game.api.components.Room2D;");
                print(scene, "import org.dolphin.game.api.components.Tile;");
                print(scene, "import org.dolphin.game.api.components.View;");
                print(scene,"import org.dolphin.game.api.components.*;");
                
                print(scene, "import org.dolphin.game.api.types.*;");

                print(scene, "public class " + r.getName() + " extends org.dolphin.game.api.components.Room2D {");
                print(scene, "");
                print(scene, "	public " + r.getName() + "(int vectorid) {");
                print(scene, "		super(Game.frame,\"" + r.get(PRoom.CAPTION) + "\"," + r.get(PRoom.SPEED) + "," + r.get(PRoom.WIDTH) + "," + r.get(PRoom.HEIGHT) + ",new Color(" + ((Color)r.get(PRoom.BACKGROUND_COLOR)).getRed() + "," + ((Color)r.get(PRoom.BACKGROUND_COLOR)).getGreen() + "," + ((Color)r.get(PRoom.BACKGROUND_COLOR)).getBlue() + ")," + r.get(PRoom.DRAW_BACKGROUND_COLOR) + "," + r.get(PRoom.PERSISTENT) + "," + r.getId() + ");");
                print(scene, "          this.vectorid=vectorid;");
                print(scene, "  }");
                
                print(scene, "public void Creation_code(){");
                print(scene, " " + parseGCL(""+r.get(PRoom.CREATION_CODE)));
                print(scene, "}");


                print(scene, "");
                print(scene, "  protected void setupScene() {");
                print(scene, "");

                /*create the instances*/
                for (int i = 0; i < r.instances.size(); i++) {
                    Instance in = r.instances.get(i);
                    ;
                    print(scene, "    instances.add(new " + ((ResourceReference<GmObject>)in.properties.get(PInstance.OBJECT)).get().getName() + "(" + in.getPosition().x + ", " + in.getPosition().y + ", " + (Integer)in.properties.get(PInstance.ID) + "));");
                }
                print(scene, "    /*Create the backgrounds*/");
                /*Create the backgrounds*/
                for (int i = 0; i < r.backgroundDefs.size(); i++) {
                    BackgroundDef b = r.backgroundDefs.get(i);
                    
                    if (b.properties.get(PBackgroundDef.BACKGROUND) != null) {
                        print(scene, "backgrounds.add(new Background(" + b.properties.get(PBackgroundDef.VISIBLE) + "," + b.properties.get(PBackgroundDef.FOREGROUND) + "," + b.properties.get(PBackgroundDef.X) + "," + b.properties.get(PBackgroundDef.Y) + "," + ((ResourceReference<Background>)b.properties.get(PBackgroundDef.BACKGROUND)).get().getWidth() + "," + ((ResourceReference<Background>)b.properties.get(PBackgroundDef.BACKGROUND)).get().getHeight() + "," + b.properties.get(PBackgroundDef.TILE_HORIZ) + "," + b.properties.get(PBackgroundDef.TILE_VERT) + "," + b.properties.get(PBackgroundDef.STRETCH) + "," + b.properties.get(PBackgroundDef.H_SPEED) + "," + b.properties.get(PBackgroundDef.V_SPEED) + "," + ((ResourceReference<Background>)b.properties.get(PBackgroundDef.BACKGROUND)).get().get(PBackground.SMOOTH_EDGES) + "," + ((ResourceReference<Background>)b.properties.get(PBackgroundDef.BACKGROUND)).get().get(PBackground.TRANSPARENT) + ",Game.thegame.loadBackground(\"" + ((ResourceReference<Background>)b.properties.get(PBackgroundDef.BACKGROUND)).get().getName() + "\")));");
                    }
                //TODO fix the above code to write backgrounds
                }

                print(scene, "    /*Create the tiles*/");
                /*Create the tiles*/
                for (int i = 0; i < r.tiles.size(); i++) {
                    Tile t = r.tiles.get(i);
                    if (t != null) {
                        print(scene, "tiles.add(new Tile(" + t.getRoomPosition().x + ", " + t.getRoomPosition().y + "," + t.getBackgroundPosition().x + ", " + t.getBackgroundPosition().y + ", " + t.getSize().width + "," + t.getSize().height + "," + t.getDepth() + ", " + t.properties.get(PTile.ID) + ",Game.thegame.loadBackground(\"" + ((ResourceReference<Background>)t.properties.get(PTile.BACKGROUND)).get().getName() + "\")));");
                    }
                }

                print(scene, "    /*Create the views*/");
                print(scene, "     this.showviews=" + r.get(PRoom.ENABLE_VIEWS) + ";");
                /*Create the tiles*/
                for (int i = 0; i < r.views.size(); i++) {
                    View v = r.views.get(i);
                    
                    if (v != null) {
                        if (v.properties.get(PView.VISIBLE)) {
                            print(scene, "views.add(new View(" + v.properties.get(PView.VIEW_X) + ", " + v.properties.get(PView.VIEW_Y) + "," + v.properties.get(PView.VIEW_W) + ", " + v.properties.get(PView.VIEW_H) + ", " + v.properties.get(PView.PORT_X) + "," + v.properties.get(PView.PORT_Y) + "," + v.properties.get(PView.PORT_W) + ", " + v.properties.get(PView.PORT_H) + "," + v.properties.get(PView.SPEED_H) + "," + v.properties.get(PView.SPEED_V) + "," + v.properties.get(PView.BORDER_H) + "," + v.properties.get(PView.BORDER_V) + "," + v.properties.get(PView.VISIBLE) + "));");
                        }
                    }
                }
                print(scene,"createEvents();");
                print(scene, "");
                print(scene, "  }");//end setupScene
                print(scene, "}");//end class
                scene.close();
            } catch (Exception e) {
                showException(e);
            }
        }
    }

    /*
     * Print a line to the BufferedWriter
     */
    public static void print(BufferedWriter file, String printString) throws IOException {
        file.write(printString);
        file.newLine();
    }

    public static void deleteFiles( String directory, String extension ) {
    ExtensionFilter filter = new ExtensionFilter(extension);
    File dir = new File(directory);

    String[] list = dir.list(filter);
    File file;
    if (list.length == 0) return;

    for (int i = 0; i < list.length; i++) {
      //file = new File(directory + list[i]);
      file = new File(directory, list[i]);
      //System.out.print(file + "  deleted : " + file.delete());
      file.delete();
    }
   }

}

class ExtensionFilter implements FilenameFilter {
  private String extension;
  public ExtensionFilter( String extension ) {
    this.extension = extension;
  }

  public boolean accept(File dir, String name) {
    return (name.endsWith(extension));
  }
}

class Transparency
{
public static BufferedImage makeColorTransparent(Image im,final Color color)
	{
	ImageFilter filter = new RGBImageFilter()
		{
			// the color we are looking for... Alpha bits are set to opaque
			public int markerRGB = color.getRGB() | 0xFF000000;

			public final int filterRGB(int x,int y,int rgb)
				{
				if ((rgb | 0xFF000000) == markerRGB)
					{
					// Mark the alpha bits as zero - transparent
					return 0x00FFFFFF & rgb;
					}
				else
					{
					// nothing to do
					return rgb;
					}
				}
		};
	ImageProducer ip = new FilteredImageSource(im.getSource(),filter);
	return createBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
	}

private static BufferedImage createBufferedImage(Image image)
	{
	BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),
			BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = bi.createGraphics();
	g.drawImage(image,0,0,null);

	return bi;
	}

}

