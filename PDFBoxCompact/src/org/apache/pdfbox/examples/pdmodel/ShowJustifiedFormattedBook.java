package org.apache.pdfbox.examples.pdmodel;

import java.io.IOException;

import org.apache.pdfbox.breakintolines.BreakIntoDocLines;
import org.apache.pdfbox.breakintolines.DocumentSettings;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class ShowJustifiedFormattedBook {

	static String isaiah53 = 
		"\000\00153\000\000 Who has believed our report? And to whom "+
		"has the arm of the Lord been revealed? \000\0022\000\000 "+
		"For He shall grow up before Him as a tender plant, and as "+
		"a root out of dry ground. He has no form or comeliness; "+
		"and when we see Him, there is no beauty that we should desire "+
		"Him. \000\0023\000\000 He is despised and rejected by men, "+
		"a man of sorrows and acquainted with grief. And we hid, "+
		"as it were, our faces from Him; He was despised, and we "+
		"did not esteem Him. \000\0024\000\000 Surely He has borne "+
		"our griefs and carried our sorrows; yet we esteemed Him "+
		"stricken, smitten by God, and a\ufb04icted. \000\0025\000\000 "+
		"But He was wounded for our transgressions, He was bruised "+
		"for our iniquities; the chastisement for our peace was upon "+
		"Him, and by His stripes we are healed. \000\0026\000\000 "+
		"All we like sheep have gone astray; we have turned, every "+
		"one, to his own way; and the Lord has laid on Him the iniquity "+
		"of us all. \000\0027\000\000 He was oppressed and He was "+
		"a\ufb04icted, yet He opened not His mouth; He was led as a "+
		"lamb to the slaughter, and as a sheep before its shearers "+
		"is silent, so He opened not His mouth. \000\0028\000\000 "+
		"He was taken from prison and from judgment, and who will "+
		"declare His generation? For He was cut off from the land "+
		"of the living; for the transgressions of My people He was "+
		"stricken. \000\0029\000\000 And they made His grave with "+
		"the wicked— but with the rich at His death, because He had "+
		"done no violence, nor was any deceit in His mouth. \000\00210\000\000 "+
		"Yet it pleased the Lord to bruise Him; He has put Him to "+
		"grief. When you make His soul an offering for sin, He shall "+
		"see His seed, He shall prolong His days, and the pleasure "+
		"of the Lord shall prosper in His hand. \000\00211\000\000 "+
		"He shall see the labor of His soul, and be satisfied. By "+
		"His knowledge My righteous servant shall justify many, for "+
		"He shall bear their iniquities. \000\00212\000\000 Therefore "+
		"I will divide Him a portion with the great, and He shall "+
		"divide the spoil with the strong, because He poured out "+
		"His soul unto death, and He was numbered with the transgressors, "+
		"and He bore the sin of many, and made intercession for the "+
		"transgressors. \n \n \000\00154\000\000 \000\003“Sing, O "+
		"barren, you who have not borne! Break forth into singing, "+
		"and cry aloud, you who have not labored with child! For "+
		"more are the children of the desolate than the children "+
		"of the married woman,”\000\000 says the Lord. \000\0022\000\000 "+
		"\000\003“Enlarge the place of your tent, and let them stretch "+
		"out the curtains of your dwellings; do not spare; lengthen "+
		"your cords, and strengthen your stakes. \000\0023\000\003 "+
		"For you shall expand to the right and to the left, and your "+
		"descendants will inherit the nations, and make the desolate "+
		"cities inhabited. \000\0024\000\003 “Do not fear, for you "+
		"will not be ashamed; neither be disgraced, for you will "+
		"not be put to shame; for you will forget the shame of your "+
		"youth, and will not remember the reproach of your widowhood "+
		"anymore. \000\0025\000\003 For your maker is your husband, "+
		"the Lord of hosts is His name; and your redeemer is the "+
		"holy one of Israel; He is called the God of the whole earth. "+
		"\000\0026\000\003 For the Lord has called you like a woman "+
		"forsaken and grieved in spirit, like a youthful wife when "+
		"you were refused,”\000\000 says your God. \000\0027\000\000 "+
		"\000\003“For a mere moment I have forsaken you, but with "+
		"great mercies I will gather you. \000\0028\000\003 With "+
		"a little wrath I hid My face from you for a moment; but "+
		"with everlasting kindness I will have mercy on you,”\000\000 "+
		"says the Lord, your redeemer. \000\0029\000\000 \000\003“For "+
		"this is like the waters of Noah to Me; for as I have sworn "+
		"that the waters of Noah would no longer cover the earth, "+
		"so have I sworn that I would not be angry with you, nor "+
		"rebuke you. \000\00210\000\003 For the mountains shall depart "+
		"and the hills be removed, but My kindness shall not depart "+
		"from you, nor shall My covenant of peace be removed,”\000\000 "+
		"says the Lord, who has mercy on you. \000\00211\000\000 "+
		"\000\003“O you a\ufb04icted one, tossed with tempest, and not "+
		"comforted, behold, I will lay your stones with colorful "+
		"gems, and lay your foundations with sapphires. \000\00212\000\003 "+
		"I will make your pinnacles of rubies, your gates of crystal, "+
		"and all your walls of precious stones. \000\00213\000\003 "+
		"All your children shall be taught by the Lord, and great "+
		"shall be the peace of your children. \000\00214\000\003 "+
		"In righteousness you shall be established; you shall be "+
		"far from oppression, for you shall not fear; and from terror, "+
		"for it shall not come near you. \000\00215\000\003 Indeed "+
		"they shall surely assemble, but not because of Me. Whoever "+
		"assembles against you shall fall for your sake. \000\00216\000\003 "+
		"“Behold, I have created the blacksmith who blows the coals "+
		"in the fire, who brings forth an instrument for his work; "+
		"and I have created the spoiler to destroy. \000\00217\000\003 "+
		"No weapon formed against you shall prosper, and every tongue "+
		"which rises against you in judgment you shall condemn. This "+
		"is the heritage of the servants of the Lord, and their righteousness "+
		"is from me,”\000\000 says the Lord. \n \n \000\00155\000\000 "+
		"\000\003“Ho! Everyone who thirsts, come to the waters; and "+
		"you who have no money, come, buy and eat. Yes, come, buy "+
		"wine and milk without money and without price. \000\0022\000\003 "+
		"Why do you spend money for what is not bread, and your wages "+
		"for what does not satisfy? Listen carefully to Me, and eat "+
		"what is good, and let your soul delight itself in abundance. "+
		"\000\0023\000\003 Incline your ear, and come to Me. Hear, "+
		"and your soul shall live; and I will make an everlasting "+
		"covenant with you— the sure mercies of David. \000\0024\000\003 "+
		"Indeed I have given him as a witness to the people, a leader "+
		"and commander for the people. \000\0025\000\003 Surely you "+
		"shall call a nation you do not know, and nations who do "+
		"not know you shall run to you, because of the Lord your "+
		"God, and the holy one of Israel; for He has glorified you.”\000\000 "+
		"\000\0026\000\000 Seek the Lord while He may be found, call "+
		"upon Him while He is near. \000\0027\000\000 Let the wicked "+
		"forsake his way, and the unrighteous man his thoughts; let "+
		"him return to the Lord, and He will have mercy on him; and "+
		"to our God, for He will abundantly pardon. \000\0028\000\000 "+
		"\000\003“For My thoughts are not your thoughts, nor are "+
		"your ways My ways,”\000\000 says the Lord. \000\0029\000\000 "+
		"\000\003“For as the heavens are higher than the earth, so "+
		"are My ways higher than your ways, and My thoughts than "+
		"your thoughts. \000\00210\000\003 “For as the rain comes "+
		"down, and the snow from heaven,And do not return there, "+
		"but water the earth, and make it bring forth and bud, that "+
		"it may give seed to the sower and bread to the eater, \000\00211\000\003 "+
		"so shall My word be that goes forth from My mouth; it shall "+
		"not return to Me void, but it shall accomplish what I please, "+
		"and it shall prosper in the thing for which I sent it. \000\00212\000\003 "+
		"“For you shall go out with joy, and be led out with peace; "+
		"the mountains and the hills shall break forth into singing "+
		"before you, and all the trees of the field shall clap their "+
		"hands. \000\00213\000\003 Instead of the thorn shall come "+
		"up the cypress tree, and instead of the brier shall come "+
		"up the myrtle tree; and it shall be to the Lord for a name, "+
		"for an everlasting sign that shall not be cut off.”\000\000 "+
		"\n \n \000\00156\000\000 Thus says the Lord: \000\003“Keep "+
		"justice, and do righteousness, for My salvation is about "+
		"to come, and My righteousness to be revealed. \000\0022\000\003 "+
		"Blessed is the man who does this, and the son of man who "+
		"lays hold on it; who keeps from defiling the sabbath, and "+
		"keeps his hand from doing any evil.”\000\000 \000\0023\000\000 "+
		"Do not let the son of the foreigner who has joined himself "+
		"to the Lord speak, saying, \000\003“The Lord has utterly "+
		"separated me from His people”\000\000; nor let the eunuch "+
		"say, \000\003“Here I am, a dry tree.”\000\000 \000\0024\000\000 "+
		"For thus says the Lord: \000\003“To the eunuchs who keep "+
		"My sabbaths, and choose what pleases Me, and hold fast My "+
		"covenant, \000\0025\000\003 even to them I will give in "+
		"My house and within My walls a place and a name better than "+
		"that of sons and daughters; I will give them an everlasting "+
		"name that shall not be cut off. \000\0026\000\003 “Also "+
		"the sons of the foreigner who join themselves to the Lord, "+
		"to serve Him, and to love the name of the Lord, to be His "+
		"servants— everyone who keeps from defiling the sabbath, "+
		"and holds fast My covenant— \000\0027\000\003 even them "+
		"I will bring to My holy mountain, and make them joyful in "+
		"My house of prayer. Their burnt offerings and their sacrifices "+
		"will be accepted on My altar; for My house shall be called "+
		"a house of prayer for all nations.”\000\000 \000\0028\000\000 "+
		"The Lord God, who gathers the outcasts of Israel, says, "+
		"\000\003“Yet I will gather to him others besides those who "+
		"are gathered to him.”\000\000 \000\0029\000\000 All you "+
		"beasts of the field, come to devour, all you beasts in the "+
		"forest. \000\00210\000\000 His watchmen are blind, they "+
		"are all ignorant; they are all dumb dogs, they cannot bark; "+
		"sleeping, lying down, loving to slumber. \000\00211\000\000 "+
		"Yes, they are greedy dogs which never have enough. And they "+
		"are shepherds who cannot understand; they all look to their "+
		"own way, every one for his own gain, from his own territory.";


	 public static void main(String[] args) throws IOException {
		 float fs = 7.5f;

		 BreakIntoDocLines bl = new BreakIntoDocLines(PDRectangle.A4);
		 DocumentSettings set = bl.getDocumentSettings();
		 String f0 = "C:\\Windows\\Fonts\\OpenSans-Italic.ttf";
		 set.initializeCurrentfont(f0, fs);
		 String f1 = "C:\\Windows\\Fonts\\OpenSans-BoldItalic.ttf";
		 set.addFont(f1, 16f);
		 String f2 = "C:\\Windows\\Fonts\\OpenSans-BoldItalic.ttf";
		 set.addFont(f2, fs);
		 String f3 = "C:\\Windows\\Fonts\\OpenSans-CondBold.ttf";
		 set.addFont(f3, 8f);
		 String f4 = "C:\\Windows\\Fonts\\OpenSans-CondBold.ttf";
		 set.addFont(f4, 6.5f);
//		 String f5 = "C:\\Windows\\Fonts\\OpenSans-SemiboldItalic.ttf";
//		 set.addFont(f5, fs);

		 //set.setInputString(isaiah53);
		 set.setInputFile("../output/Isaiah.txt");
		 bl.setlineheight(9f);
		 bl.settitle("Isaiah", 4);
		 bl.firstpage(1);
		 bl.setminimumspace(3f);
		 bl.convert2book();
	 }
	
	
}
