package org.openstreetmap.josm.data.validation.tests;

import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.Relation;
import org.openstreetmap.josm.data.validation.Severity;
import org.openstreetmap.josm.data.validation.Test;
import org.openstreetmap.josm.data.validation.TestError;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.openstreetmap.josm.tools.I18n.marktr;
import static org.openstreetmap.josm.tools.I18n.tr;

public class GeorgianToponyms extends Test {

    protected static final int WRONG_NAME_KA = 4300;
    protected static final int WRONG_NAME_EN = 4301;

    // Data from
    // https://geonetwork.napr.gov.ge/geonetwork/srv/eng/catalog.search;jsessionid=1E5BD4EC344515648111D65D8073F038#/metadata/be16a17f-96b8-43bf-a25d-bc000cb5d52d
    // https://data.humdata.org/dataset/cod-ab-geo | geo_supplimentary_gazetteer_2019.xlsx | "administraciuli erTeulebi", National Statistics Office of Georgia
    // https://data.humdata.org/dataset/cod-ab-geo | GEO_AdminBoundaries_TabularData.xlsx
    private final List<String> officialGeorgianTownNamesEn = Arrays.asList(
            "Abasha", "Adigeni", "Akhalkalaki", "Akhaltsikhe", "Akhaltsikhe City", "Akhmeta", "Ambrolauri",
            "Ambrolauri City", "Aspindza", "Baghdati", "Batumi", "Bolnisi", "Borjomi", "Chiatura", "Chkhorotsku",
            "Chokhatauri", "Dedoplistskaro", "Dmanisi", "Dusheti", "Gardabani", "Gori", "Gori City", "Gurjaani",
            "Kareli", "Kaspi", "Kazbegi", "Keda", "Kharagauli", "Khashuri", "Khelvachauri", "Khobi", "Khoni", "Khulo",
            "Kobuleti", "Kutaisi", "Kvareli", "Lagodekhi", "Lanchkhuti", "Lentekhi", "Marneuli", "Martvili", "Mestia",
            "Mtskheta", "Mtskheta City", "Ninotsminda", "Oni", "Ozurgeti", "Ozurgeti City", "Poti", "Rustavi",
            "Sachkhere", "Sagarejo", "Samtredia", "Senaki", "Shuakhevi", "Sighnaghi", "Telavi", "Telavi City",
            "Terjola", "Tetritskaro", "Tianeti", "Tkibuli", "Tsageri", "Tsalenjikha", "Tsalka", "Tskaltubo", "Vani",
            "Zestaponi", "Zugdidi", "Zugdidi City", "Zahesi", "Gldani", "Tskneti", "Ponichala", "Kojori", "Didi Lilo",
            "Gagra", "Gantiadi", "Bichvinta", "Bzipi", "Alakhadzi", "Baghnari", "Kolkhida", "Leselidze", "Lidzavi",
            "Mekhadiri", "Mikelripshi", "Khashupsa", "Kheivani", "Kholodnaia-Rechka", "Gali", "Achigvara", "Bedia",
            "Ganakhleba", "Gudava", "Dikhazurga", "Zemo Barghebi", "Tagiloni", "Lekukhona", "Mziuri", "Mukhuri",
            "Nabakevi", "Otobaia", "Okumi", "Pirveli Gali", "Repo-Shesheleti", "Rechkhi", "Saberio", "Sida", "Pichori",
            "Kvemo Barghebi", "Ghumurishi", "Chkhortoli", "Tsarche", "Chuburkhinji", "Khumushkuri", "Gudauta",
            "Akhali Atoni", "Miusera", "Aatsi", "Abgharuki", "Agaraki", "Anukhva", "Achandara", "Akhalsopeli",
            "Barmishi", "Blaburkhva", "Duripshi", "Zvandripshi", "Kaldakhvara", "Likhni", "Mgudzirkhva", "Mtsara",
            "Otkhara", "Primorskoe", "Psirtskha", "Kulanurkhva", "Khopi", "Jirkhva", "Gulripshi", "Amtkeli",
            "Babushara", "Baghmarani", "Dranda", "Vladimirovka", "Lata", "Matchara", "Merkheuli", "Pshapi", "Tsebelda",
            "Ochamchire", "Agubedia", "Atari-Armianskaia", "Atara", "Aradu", "Arakichi", "Arasadzikhi", "Adziubzha",
            "Akhali Kindghi", "Beslakhuba", "Gupi", "Tkhina", "Ilori", "Kindghi", "Kutoli", "Labra", "Merkula", "Mokvi",
            "Otapi", "Okhurei", "Reka", "Tamishi", "Tqvarcheli", "Pokveshi", "Kochara", "Ghvada", "Tchlou", "Jgerda",
            "Akhalsheni", "Besleti", "Gumista", "Eshera", "Verkhnaia-Eshera", "Tavisupleba", "Kelasuri", "Odishi",
            "Pskhu", "Shroma", "Azhara", "Dandalo", "Dologani", "Zvare", "Makhuntseti", "Merisi", "Oktomberi",
            "Pirveli Maisi", "Tskhmorisi", "Tsoniarisi", "Ochkhamuri", "Chakvi", "Alambari", "Atchqvistavi",
            "Bobokvati", "Gvara", "Dagva", "Zeniti", "Kvirike", "Leghva", "Mukhaestate", "Sachino", "Kakuti",
            "Chaisubani", "Tsikhisdziri", "Tskavroka", "Tchakhati", "Khala", "Khutsubani", "Baratauli", "Dghvani",
            "Zamleti", "Oladauri", "Uchamba", "Shubani", "Tskalsakari", "Tchvana", "Acharistskali", "Tkhilnari",
            "Kirnati", "Matchakhela", "Makhinjauri", "Ortabatumi", "Sarpi", "Peria", "Sharabidzeebi", "Tcharnali",
            "Agara", "Dekanashvilebi", "Didachara", "Dioknisi", "Vashlovani", "Tkhilvana", "Riketi", "Satsikhuri",
            "Skhalta", "Pushrukauli", "Ghorjomi", "Khikhadziri", "Aketi", "Atsana", "Gvimbalauri", "Lesa-Tchinati",
            "Mamati", "Machkhvareti", "Nigvziani", "Nigoiti", "Ninoshvili", "Supsa", "Ghrmaghele", "Shukhuti",
            "Chibati", "Chochkhati", "Jurukveti", "Laituri", "Naruja", "Nasakirali", "Ureki", "Askana", "Baileti",
            "Bakhvi", "Bokhvauri", "Gurianta", "Dvabzu", "Vakijvari", "Tkhinvali", "Kontchkati", "Likhauri",
            "Makvaneti", "Melekeduri", "Meria", "Mtispiri", "Nagomari", "Natanebi", "Silauri", "Shemokmedi",
            "Tskhemliskhidi", "Dzimiti", "Tchanieti", "Jumati", "Amaghleba", "Bukistsikhe", "Gogolesubani",
            "Goraberezhouli", "Guturi", "Dablatsikhe", "Didivani", "Erketi", "Vanzomleti", "Zemo Surebi", "Zemokheti",
            "Zoti", "Kokhnari", "Nabeghlavi", "Satchamiaseri", "Partskhma", "Kvenobani", "Shua Amaghleba",
            "Shua Surebi", "Khevi", "Khidistavi", "Jvartskhma", "Dimi", "Vartsikhe", "Zegani", "Meore Obcha",
            "Nergeeti", "Pirveli Obcha", "Rokiti", "Rokhi", "Sakraula", "Persati", "Tsitelkhevi", "Khani", "Bzvani",
            "Gadidi", "Gora", "Dikhashkho", "Zeda Vani", "Zeindari", "Mtisdziri", "Mukedi", "Salominao", "Salkhino",
            "Saprasia", "Sulori", "Tobanieri", "Ukhuti", "Pereta", "Kumuri", "Shuamta", "Tsikhesulori", "Dzulukhi",
            "Boslevi", "Dilikauri", "Zeda Sakara", "Zovreti", "Ilemi", "Kvaliti", "Kldeeti", "Meore Sviri",
            "Pirveli Sviri", "Rodinauli", "Sanakhshire", "Puti", "Kveda Sazano", "Kveda Sakara", "Shorapani", "Shrosha",
            "Tskhratskaro", "Dzirula", "Alisubani", "Akhali Terjola", "Bardubani", "Gogni", "Godogani", "Etseri",
            "Zeda Sazano", "Zeda Simoneti", "Tuzi", "Kvakhtchiri", "Nakhshirghele", "Rupoti", "Siktarva",
            "Kveda Simoneti", "Ghvankiti", "Chkhari", "Dzevri", "Chognari", "Kulashi", "Bashi", "Gamochinebuli", "Gomi",
            "Gormaghali", "Didi Jikhaishi", "Ianeti", "Melauri", "Opeti", "Sajavakho", "Tolebi", "Ghaniri", "Argveti",
            "Gorisi", "Korbouli", "Merjevi", "Sairkhe", "Sareki", "Koreti", "Chikha", "Tskhomareti", "Tchala",
            "Tchalovani", "Jalaurta", "Gurna", "Kursebi", "Mukhura", "Orpiri", "Satsire", "Sochkheti", "Tsutskhvati",
            "Khresili", "Jvarisa", "Geguti", "Gvishtibi", "Gumbra", "Dghnorisa", "Maghlaki", "Mekvena", "Mukhiani",
            "Opurchkheti", "Opshkviti", "Patriketi", "Rioni", "Sakulia", "Partskhanaqanevi", "Kvitiri", "Tskhunkuri",
            "Tsqaltubo", "Gezruli", "Vatchevi", "Zodi", "Itkhvisi", "Katskhi", "Mandaeti", "Nigozeti", "Perevisa",
            "Rgani", "Sarkveletubani", "Sveri", "Kvatsikhe", "Tsirkvali", "Khvashiti", "Khreiti", "Bazaleti", "Boriti",
            "Vardzia", "Vakhani", "Kitskhi", "Lashe", "Leghvani", "Moliti", "Nadaburi", "Sargveshi", "Saghandzile",
            "Partskhnali", "Ghoresha", "Tsipa", "Tskalaporeti", "Khidari", "Khunevi", "Gordi", "Gochajikhaishi", "Gubi",
            "Dedalauri", "Ivandidi", "Kinchkha", "Kukhi", "Matkhoji", "Nakhakhulevi", "Kutiri", "Dzedzileti", "Duisi",
            "Zemo Alvani", "Zemo Khodasheni", "Tusheti (Omalo)", "Kasristskali", "Matani", "Maghnaari", "Ozhio",
            "Sakobiano", "Kvemo Alvani", "Kistauri", "Shakhvetila", "Khalatsani", "Joqolo", "Arashenda", "Akhasheni",
            "Bakurtsikhe", "Vazisubani", "Vachnadziani", "Velistsikhe", "Vejini", "Zemo Katchreti", "Kalauri",
            "Kardenakhi", "Katchreti", "Kolagi", "Melaani", "Mukuzani", "Naniani", "Shashiani", "Chalaubani",
            "Chumlaki", "Dzirkoki", "Tchandari", "Tcheremi", "Jimiti", "Arboshiki", "Arkhiloskalo", "Gamarjveba",
            "Zemo Machkhaani", "Zemo Kedi", "Mirzaani", "Ozaani", "Sabatlo", "Samtatskaro", "Samreklo", "Pirosmani",
            "Kvemo Kedi", "Khornabuji", "Akura", "Artana", "Busheti", "Gulgula", "Vanta", "Vardisubani", "Tetritsklebi",
            "Ikalto", "Kisiskhevi", "Kondoli", "Kurdghelauri", "Laliskuri", "Lapankuri", "Nasamkhrali", "Napareuli",
            "Ruispiri", "Saniore", "Pshaveli", "Kvemo Khodasheni", "Karajala", "Shalauri", "Tsinandali", "Areshperani",
            "Apeni", "Baisubani", "Giorgeti", "Kabali", "Kartubani", "Leliani", "Matsimi", "Ninigori", "Pona",
            "Tsodniskari", "Tchiauri", "Badiauri", "Giorgitsminda", "Gombori", "Didi Chailuri", "Duzagrama",
            "Verkhviani", "Tokhliauri", "Tulari", "Iormughanlo", "Kakabeti", "Kotchbaani", "Lambalo", "Manavi",
            "Mzisguli", "Patara Chailuri", "Patardzeuli", "Sataple", "Udabno", "Ujarma", "Kandaura", "Shibliani",
            "Tskarostavi", "Khashmi", "Tsnori", "Anaga", "Bodbe", "Bodbiskhevi", "Vakiri", "Iliatsminda", "Magharo",
            "Nukriani", "Sakobo", "Tibaani", "Kvemo Machkhaani", "Dzveli Anaga", "Jugaani", "Balghojiani", "Gavazi",
            "Gremi", "Eniseli", "Kutchatani", "Sabue", "Shilda", "Tchikaani", "Akhalgori", "Boli", "Gdu", "Zakhori",
            "Korinta", "Largvisi", "Salbieri", "Karchokhi", "Tskhradzma", "Tsinagari", "Zhinvali", "Pasanauri",
            "Ananuri", "Gremiskhevi", "Gudamakari", "Lapanaantkari", "Magharoskari", "Mchadijvari", "Ukanpshavi",
            "Kvesheti", "Shatili", "Tchartali", "Tchonkadze", "Tchoporti", "Khevsureti", "Kheoba", "Sioni", "Artani",
            "Zaridzeebi", "Nakalakari", "Zhebota", "Simoniantkhevi", "Tushurebi", "Ghulelebi", "Chekuraantgori",
            "Khevsurtsopeli", "Aghdgomliantkari", "Galavani", "Vaziani", "Lisi", "Misaktsieli", "Mukhrani", "Nichbisi",
            "Saguramo", "Ksani", "Ksovrisi", "Tskhvarichamia", "Dzalisi", "Dzegvi", "Tserovani", "Tsilkani",
            "Stepantsminda", "Goristsikhe", "Gudauri", "Kobi", "Sno", "Bugeuli", "Velevi", "Znakva", "Itsa", "Likheti",
            "Namanevi", "Nikortsminda", "Sadmeli", "Skhvava", "Ghadishi", "Tsakhi", "Tsesi", "Tchiaghele", "Tchrebalo",
            "Tchkvishi", "Khvanchkara", "Khodikari", "Khotevi", "Zhakhunderi", "Rtskhmeluri", "Chikhareshi", "Choluri",
            "Tsani", "Kheledi", "Khopuri", "Glola", "Kvashkhieti", "Kirovi", "Komandeli", "Mravaldzali", "Pipileti",
            "Sakao", "Sori", "Utsera", "Parakheti", "Ghari", "Ghebi", "Sheubani", "Shkmeri", "Chasavali", "Tskhmori",
            "Tsedisi", "Tchiora", "Alpana", "Aghvi", "Zubi", "Lailashi", "Lasuriashi", "Lukhvano", "Nakuraleshi",
            "Orbeli", "Okureshi", "Sairme", "Spatagori", "Tvishi", "Usakhelo", "Kveda Tsageri", "Ghvirishi", "Chkumi",
            "Chkhuteli", "Tsiperchi", "Gezati", "Zanati", "Ketilari", "Marani", "Naesakao", "Norio", "Ontopo",
            "Samikao", "Sepieti", "Sujuna", "Tkviri", "Kolobani", "Dzveli Abasha", "Tskemi", "Abastumani", "Anaklia",
            "Akhali Abastumani", "Akhalkakhati", "Ganarjiis Mukhuri", "Grigolishi", "Darcheli", "Didi Nedzi", "Ergeta",
            "Ingiri", "Kakhati", "Koki", "Kortskheli", "Narazeni", "Orsantia", "Orulu", "Rike", "Rukhi", "Urta",
            "Kulishkari", "Shamgona", "Chkhoria", "Tsaishi", "Tchankviji", "Tchitatsqari", "Tchkaduashi", "Jikhaskari",
            "Abedati", "Bandza", "Gatchedili", "Gurdzemi", "Didi Chkoni", "Doshake", "Vedidkari", "Tamakoni",
            "Inchkhuri", "Kitsia", "Kurzu", "Lekhaindravo", "Nagvazavo", "Nakhunavo", "Najakhavo", "Onoghia",
            "Sergieti", "Taleri", "Khuntsi", "Becho", "Idliani", "Ipari", "Kala", "Latali", "Lakhamula", "Lenjeri",
            "Mulakhi", "Nakra", "Ushguli", "Pari", "Tskhumari", "Tsvirmi", "Tchuberi", "Khaishi", "Gejeti", "Eki",
            "Zana", "Zemo Tchaladidi", "Teklati", "Ledzadzame", "Menji", "Nosiri", "Nokalakevi", "Ushapati", "Potskho",
            "Dzveli Senaki", "Khorshi", "Akhuti", "Zumi", "Taia", "Kirtskhi", "Lesitchine", "Letsurtsume", "Nakiani",
            "Napichkhovo", "Kveda Chkhorotsqu", "Tchogha", "Khabume", "Jvari", "Lia", "Medani", "Mikava", "Muzhava",
            "Nakipu", "Obuji", "Pakhulani", "Chkvaleri", "Tchale", "Jgali", "Akhalkhibula", "Bia", "Guripuli",
            "Zemo Kvaloni", "Torsa-Dghvaba", "Nojikhevi", "Patara Poti", "Pirveli Khorga", "Sagvichio", "Sajijao",
            "Kariata", "Kvemo Kvaloni", "Kulevi", "Shavghele", "Shua Khorgi", "Dzveli Khibula", "Tchaladidi",
            "Khamiskuri", "Kheta", "Arali", "Benara", "Bolajuri", "Gorguli", "Dertseli", "Varkhani", "Zanavi", "Zarzma",
            "Zedubani", "Lelovani", "Mlashe", "Mokhe", "Ude", "Pkhero", "Chorchani", "Tsakhani", "Tchetchla",
            "Khevasheni", "Atskvita", "Damala", "Vargavi", "Idumala", "Nakalakevi", "Ota", "Orgora", "Toloshi",
            "Dzveli", "Khizabavra", "Azavreti", "Alastani", "Aragva", "Baraleti", "Gogasheni", "Diliska", "Vachiani",
            "Zakvi", "Kartikami", "Kartsakhi", "Kotelia", "Kotchio", "Kumurdo", "Okami", "Ptena", "Sulda", "Turtskhi",
            "Chunchkha", "Khaveti", "Khando", "Khospio", "Vale", "Andriatsminda", "Atskuri", "Eliatsminda", "Klde",
            "Minadze", "Pamaji", "Sadzeli", "Sviri", "Skhvilisi", "Uraveli", "Persa", "Tskaltbila", "Tskruti",
            "Akhaldaba", "Bakuriani", "Tsaghveri", "Balabta", "Gverdisubani", "Gujareti", "Dviri", "Tabatskuri",
            "Tadzrisi", "Tba", "Kvibisi", "Tsikhisjvari", "Gandza", "Gorelovka", "Gondrio", "Eshtia", "Satkhe",
            "Tambovka", "Phoka", "Khanchali", "Jigrasheni", "Tamarisi", "Kazreti", "Akaurta", "Darbazi", "Disveli",
            "Mamkhuti", "Nakhiduri", "Ratevani", "Tatchisubani", "Talaveri", "Tandzia", "Kvemo Bolnisi", "Kveshi",
            "Aghtaklia", "Akhali Samgori", "Vakhtangisi", "Teleti", "Kalinino", "Krtsanisi", "Kumisi", "Lemshveniera",
            "Martkopi", "Nazarlo", "Sartichala", "Kesalo", "Karataklia", "Karajalari", "Jandara", "Amamlo", "Gomareti",
            "Guguti", "Didi Dmanisi", "Irganchai", "Ipnari", "Kamarlo", "Mashavera", "Orozmani", "Sakire", "Sarkineti",
            "Karabulakhi", "Kizilkilisa", "Manglisi", "Asureti", "Borbalo", "Golteti", "Dagheti", "Durnuki", "Toneti",
            "Iraga", "Kldeisi", "Koda", "Marabda", "Orbeti", "Chkhikvta", "Tsintsqaro", "Tchivtchavi", "Jorjiashvili",
            "Algeti", "Akhkerpi", "Damia-Geurarkhi", "Kasumlo", "Kapanachkhi", "Kachagani", "Opreti", "Sadakhlo",
            "Kutliari", "Kizil-Ajlo", "Kulari", "Shaumiani", "Shulaveri", "Tserakvi", "Tsereteli", "Khojorni",
            "Bediani", "Trialeti", "Avranlo", "Aiazmi", "Artsivani", "Arjevan-Sarvani", "Ashkala", "Bareti", "Berta",
            "Beshtasheni", "Burnasheti", "Gumbati", "Darakovi", "Dashbashi", "Tejisi", "Kaburi", "Kizil-Kilisa",
            "Kokhta", "Kushi", "Nardevani", "Ozni", "Rekha", "Sameba", "Sakdarioni", "Chivt-Kilisa", "Tsintskaro",
            "Khachkovi", "Ateni", "Akhalubani", "Berbuki", "Boshuri", "Ditsi", "Variani", "Zeghduleti", "Karaleti",
            "Mereti", "Mejvriskhevi", "Mghebriani", "Nikozi", "Sakavre", "Skra", "Tiniskhidi", "Tirdznisi", "Tkviavi",
            "Kvakhvreli", "Shavshvebi", "Shindisi", "Dzevera", "Aghaiani", "Doesi", "Zemo Khandaki", "Teliani",
            "Kavtiskhevi", "Kodistskaro", "Lamiskana", "Metekhi", "Samtavisi", "Kvemo Gomi", "Kvemo Tchala",
            "Tsinarekhi", "Khovle", "Abisi", "Avlevi", "Bebnisi", "Breti", "Bredza", "Giganti", "Dvani", "Dirbi",
            "Zemo Khvedureti", "Zghuderi", "Kekhijvari", "Mokhisi", "Ruisi", "Urbnisi", "Ptsa", "Surami", "Ali",
            "Osiauri", "Plevi", "Kvishkheti", "Tsotskhnara", "Tskhramukha", "Tsaghvli", "Tsromi", "Khalebi", "Khtsisi",
            "Artsevi", "Beloti", "Berula", "Bikari", "Geri", "Vanati", "Ksuisi", "Ghromi", "Kornisi", "Avnevi", "Balta",
            "Teregvani", "Tighva", "Dzvileti", "Kemerti", "Kurta", "Dzari", "Khetagurovo", "Java", "Kvaisi", "Edisa",
            "Vaneli", "Mskhlebi", "Roka", "Keshelta", "Kemulta", "Tsona", "Khvtse", "Bakhmaro", "Tbilisi", "Sokhumi",
            "Tskhinvali", "Tkvarcheli", "Khramhesi");
    private final List<String> officialGeorgianTownNamesKa = Arrays.asList(
            "ზაჰესი", "გლდანი", "წყნეთი", "ფონიჭალა", "კოჯორი", "დიდი ლილო", "გაგრა", "განთიადი", "ბიჭვინთა", "ბზიფის",
            "ალახაძი", "ბაღნარის", "კოლხიდის", "ლესელიძის", "ლიძავის", "მეხადირის", "მიქელრიფშის", "ხაშუფშის",
            "ხეივნის", "ხოლოდნაია რეჩკას", "გალი", "აჩიგვარას", "ბედიის", "განახლების", "გუდავას", "დიხაზურგის",
            "ზემო ბარღების", "თაგილონის", "ლეკუხონის", "მზიურის", "მუხურის", "ნაბაკევის", "ოტობაიას", "ოქუმის",
            "პირველი გალის", "რეფო-შეშელეთის", "რეჩხის", "საბერიოს", "სიდა", "ფიჩორის", "ქვემო ბარღების", "ღუმურიშის",
            "ჩხორთოლის", "წარჩის", "ჭუბურხინჯის", "ხუმუშკურის", "გუდაუთის", "ახალი ათონის", "მიუსერა", "ააცის",
            "აბღარუკის", "აგარაკის", "ანუხვის", "აჭანდარის", "ახალსოფლის", "ბარმიშის", "ბლაბურხვის", "დურიფშის",
            "ზვანდრიფშის", "კალდახვარის", "ლიხნის", "მგუძირხვის", "მწარის", "ოთხარის", "პრიმორსკოეს", "ფსირცხის",
            "ყულანურხვის", "ხოფის", "ჯირხვის", "გულრიფში", "ამტყელის", "ბაბუშარას", "ბაღმარანის", "დრანდის",
            "ვლადიმიროვკის", "ლათის", "მაჭარის", "მერხეულის", "ფშაფის", "წებელდის", "ოჩამჩირეს", "აგუბედიის",
            "ატარი-არმიანსკაიას", "ათარას", "არადუს", "არაკიჩის", "არასაძიხის", "აძიუბჟის", "ახალი კინდღის",
            "ბესლახუბის", "გუფის", "თხინის", "ილორის", "კინდღის", "კუტოლის", "ლაბრა", "მერკულის", "მოქვის", "ოტაფის",
            "ოხურეის", "რეკის", "ტამიშის", "ტყვარჩელის", "ფოქვეშის", "ქოჩარის", "ღვადის", "ჭლოუს", "ჯგერდის",
            "ახალშენის", "ბესლეთის", "გუმისთის", "ეშერის", "ვერხნიაია ეშერის", "თავისუფლების", "კელასურის", "ოდიშის",
            "ფსხუს", "შრომის", "აჟარის", "ქედა", "ქედის", "დანდალოს", "დოლოგანის", "ზვარეს", "მახუნცეთის", "მერისის",
            "ოქტომბრის", "პირველი მაისის", "ცხმორისის", "წონიარისის", "ქობულეთი", "ოჩხამური", "ჩაქვის", "ალამბარის",
            "აჭყვისთავის", "ბობოყვათის", "გვარას", "დაგვა", "ზენიტი", "კვირიკეს", "ლეღვას", "მუხაესტატე", "საჩინოს",
            "ქაქუთის", "ქობულეთის", "ჩაისუბანი", "ციხისძირი", "წყავროკა", "ჭახათი", "ხალის", "ხუცუბნის", "შუახევი",
            "შუახევის", "ბარათაულის", "დღვანის", "ზამლეთის", "ოლადაურის", "უჩამბის", "შუბანის", "წყალსაყრის", "ჭვანის",
            "აჭარისწყლის", "თხილნარის", "კირნათის", "მაჭახელას", "მახინჯაურის", "ორთაბათუმის", "სარფის", "ფერიის",
            "შარაბიძის", "ჭარნალის", "ხულოს", "აგარის", "დეკანაშვილების", "დიდაჭარის", "დიოკნისის", "ვაშლოვანის",
            "თხილვანის", "რიყეთის", "საციხურის", "სხალთის", "ფუშრუკაულის", "ღორჯომის", "ხიხაძირის", "ლანჩხუთი",
            "აკეთის", "აცანის", "გვიმბალაურის", "ლესა-ჭინათის", "მამათის", "მაცხვარეთის", "ნიგვზიანის", "ნიგოითის",
            "ნინოშვილის", "სუფსის", "ღრმაღელის", "შუხუთის", "ჩიბათის", "ჩოჩხათის", "ჯურუყვეთის", "ლაითური", "ნარუჯა",
            "ნასაკირალი", "ურეკი", "ასკანის", "ბაილეთის", "ბახვის", "ბოხვაურის", "გურიანთის", "ძვაბზუს", "ვაკიჯვრის",
            "თხინვალის", "კონჭკათის", "ლიხაურის", "მაკვანეთის", "მელექედური", "მერიის", "მთისპირის", "ნაგომრის",
            "ნატანების", "ოზურგეთი", "სილაური", "შემოქმედის", "ცხმელისხიდის", "ძიმითის", "ჭანიეთის", "ჯუმათის",
            "ჩოხატაური", "ამაღლების", "ბუკისციხის", "გოგოლესუბანი", "გორაბერეჟოული", "გუთური", "დაბლაციხის",
            "დიდივანის", "ერკეთის", "ვანზომლეთის", "ზემო სურების", "ზემოხეთის", "ზოტი", "კოხნარი", "ნაბეღლავის",
            "საჭამიასერის", "ფარცხმის", "ქვენობნის", "შუა ამაღლება", "შუა სურები", "ხევის", "ხიდისთავის", "ჯვარცხმის",
            "ბაღდათი", "დიმის", "ვარციხე", "ზეგნის", "მეორე ობჩა", "ნერგეეთის", "პირველი ობჩა", "როკითის", "როხი",
            "საკრაულა", "ფერსათის", "წითელხევი", "ხანის", "ვანი", "ბზვანის", "გადიდის", "გორის", "დიხაშხოს",
            "ზედა ვანის", "ზეინდრის", "მთისძირის", "მუქედის", "სალომინაოს", "სალხინოს", "საფრასიის", "სულორი",
            "ტობანიერის", "უხუთის", "ფერეთის", "ყუმურის", "შუამთის", "ციხესულორი", "ძულუხი", "ზესტაფონი", "ბოსლევის",
            "დილიკაურის", "ზედა საქარის", "ზოვრეთი", "ილემის", "კვალითის", "კლდეეთის", "მეორე სვირის", "პირველი სვირი",
            "როდინაულის", "სანახშირის", "ფუთი", "ქვედა საზანოს", "ქვედა საქარის", "შორაპანი", "შროშის", "ცხრაწყაროს",
            "ძირულის", "თერჯოლა", "ალისუბნის", "ახალი თერჯოლის", "ბარდუბნის", "გოგნის", "გოდოგანის", "ეწერის",
            "ზედა საზანოს", "ზედა სიმონეთის", "ტუზის", "კვახჭირის", "ნახშირღელის", "რუფოთის", "სიქთარვის",
            "ქვედა სიმონეთი", "ღვანკითი", "ჩხარის", "ძევრის", "ჭოგნარი", "სამტრედია", "კულაში", "ბაში", "გამოჩინებული",
            "გომის", "გორმაღალი", "დიდი ჯიხაიში", "იანეთი", "მელაურის", "ოფეთის", "საჯავახოს", "ტოლების", "ღანირის",
            "საჩხერე", "არგვეთის", "გორისის", "კორბოულის", "მერჯევის", "საირხის", "სარეკის", "ქორეთი", "ჩიხის",
            "ცხომარეთის", "ჭალის", "ჭალოვანის", "ჯალაურთის", "ტყიბული", "გურნის", "კურსების", "მუხურა", "ორპირის",
            "საწირის", "სოჩხეთის", "ცუცხვათი", "ხრესილის", "ჯვარისას", "წყალტუბო", "გეგუთი", "გვიშტიბის", "გუმბრის",
            "დღნორისას", "მაღლაკის", "მექვენის", "მუხიანის", "ოფურჩხეთის", "ოფშკვითი", "პატრიკეთის", "რიონის",
            "საყულია", "ფარცხანაყანევი", "ქვიტირის", "ცხუნკურის", "წყალტუბოს", "ჭიათურა", "გეზრული", "ვაჭევის", "ზოდის",
            "ითხვისის", "კაცხის", "მანდაეთის", "ნიგოზეთის", "პერევისას", "რგანი", "სარქველთუბნის", "სვერის", "ქვაციხის",
            "წირქვალის", "ხვაშითის", "ხრეითი", "ხარაგაული", "ბაზალეთის", "ბორითის", "ვარძია", "ვახანის", "კიცხის",
            "ლაშეს", "ლეღვანის", "მოლითის", "ნადაბურის", "სარგვეშის", "საღანძილის", "ფარცხნალის", "ღორეშა", "წიფის",
            "წყალაფორეთის", "ხიდარი", "ხუნევის", "ხონი", "გორდის", "გოჩაჯიხაიში", "გუბის", "დედალაურის", "ივანდიდი",
            "კინჩხის", "კუხის", "მათხოჯი", "ნახახულევის", "ქუტირის", "ძეძილეთის", "ახმეტა", "ახმეტის", "დუისის",
            "ზემო ალვანის", "ზემო ხოდაშენის", "თუშეთის (ომალო)", "კასრისწყალი", "მატანი", "მაღრაანის", "ოჟიოს",
            "საქობიანოს", "ქვემო ალვანის", "ქისტაურის", "შახვეთილას", "ხალაწანის", "ჯოყოლოს", "გურჯაანი", "არაშენდის",
            "ახაშენი", "ბაკურციხე", "ვაზისუბანი", "თემი", "ველისციხე", "ვეჯინი", "ზემო კაჭრეთი", "კალაური", "კარდენახი",
            "კაჭრეთი", "კოლაგი", "მელაანი", "მუკუზანის", "ნანიანი", "შაშიანი", "ჩალაუბანი", "ჩუმლაყის", "ძირკოკი",
            "ჭანდარი", "ჭერემი", "ჯიმითი", "დედოფლისწყარო", "არბოშიკი", "არხილოსკალო", "გამარჯვება", "ზემო მაჩხაანი",
            "ზემო ქედი", "მირზაანი", "ოზაანის", "საბათლო", "სამთაწყარო", "სამრეკლო", "ფიროსმანი", "ქვემო ქედი",
            "ხორნაბუჯის", "აკურა", "ართანა", "ბუშეთი", "გულგულა", "ვანთა", "ვარდისუბანი", "თეთრიწყლები", "იყალთო",
            "კისისხევი", "კონდოლი", "კურდღელაური", "ლალისყური", "ლაფანყური", "ნასამხრალი", "ნაფარეული", "რუისპირის",
            "სანიორეს", "ფშაველის", "ქვემო ხოდაშენი", "ყარაჯალა", "სალაური", "წინანდალი", "ლაგოდეხი", "არაშფერანის",
            "აფენის", "ბაისუბნის", "გიორგეთის", "ვარდისუბნის", "კაბალის", "კართუბნის", "ლელიანის", "მაწიმის",
            "ნინიგორის", "ფონის", "ცოდნისკარის", "ჭიაურის", "საგარეჯო", "ბადიაური", "გიორგიწმინდის", "გომბორის",
            "დიდი ჩაილურის", "დუზაგრამის", "ვერხვიანი", "თოხლიაური", "თულარის", "იორმუღანლოს", "კაკაბეთის", "კოჭბაანის",
            "ლამბალოს", "მანავის", "მზისგული", "ნინოწმინდა", "პატარა ჩაილური", "პატარძეული", "სათაფლე", "უდაბნო",
            "უჯარმის", "ყანდაურის", "შიბლიანი", "წყაროსთავი", "ხაშმი", "სიღნაღი", "წნორი", "ანაგა", "ბოდბის",
            "ბოდბისხევი", "ვაქირი", "ილიაწმინდა", "მაღარო", "ნუკრიანი", "საქობოს", "ტიბაანი", "ქვემო მაჩხაანის",
            "ძველი ანაგა", "ჯუგაანის", "ყვარელი", "ბალღოჯიანი", "გავაზი", "გრემის", "ენისელი", "კუჭატნის", "მზისძირი",
            "საბუის", "შილდა", "ჭიკაანის", "ახალგორი", "ბოლის", "გდუს", "ზახორის", "კორინთის", "ლარგვისის", "სალბიერის",
            "ქარჩოხის", "ცხრაძმის", "წინაგარის", "დუშეთი", "ჟინვალის", "ფასანაურის", "ანანურის", "გრემისხევის",
            "გუდამაყრის", "ლაფანაანთკარის", "მაღაროსკარი", "მჭადიჯვრის", "უკანაფშავის", "ქვეშეთის", "შატილის",
            "ჭართლის", "ჭონქაძის", "ჭოპორტის", "ხევსურეთის", "ხეობის", "თიანეთის", "სიონის", "არტანის", "ზარიძეების",
            "ნაქალაქარის", "ჟებოტას", "სიმონიანთხევის", "ტუშურების", "ღულელების", "ჩეკურაანთგორის", "ხევსურთსოფლის",
            "აღდგომლიანთკარის", "გალავნის", "ვაზიანი", "ლისის", "მისაქციელის", "მუხრანის", "ნიჩბისის", "საგურამოს",
            "ქსანი", "ქსოვრისის", "ცხვარიჭამიის", "ძალისი", "ძეგვის", "წეროვანის", "წილკანის", "სტეფანწმინდას",
            "გორისციხის", "გუდაურის", "კობის", "სნოს", "ბუგეულის", "ველევის", "ზნაკვის", "იწის", "ლიხეთის", "ნამანევის",
            "ნიკორწმინდის", "სადმელის", "სხვავის", "ღადიშის", "ცახის", "წესის", "ჭიაღელის", "ჭრებალოს", "ჭყვიშის",
            "ხვანჭკარის", "ხოდიკარის", "ხოტევის", "ლენტეხის", "ჟახუნდერის", "რცხმელურის", "ჩიხარეშის", "ჩოლურის",
            "ცანის", "ხელედის", "ხოფურის", "ონი", "ბარის", "გლოლა", "კვაშხიეთის", "კიროვის", "კომანდელის", "მრავალძალი",
            "პიპილეთის", "საკაოს", "სორის", "უწერის", "ფარახეთის", "ღარის", "ღების", "შეუბნის", "შქმერის", "ჩასავლის",
            "ცხმორის", "წედისის", "ჭიორა", "ცაგერი", "ალპანის", "აღვის", "ზუბის", "ლაილაშის", "ლასურიაშის", "ლუხვანოს",
            "ნაკურალეშის", "ორბელის", "ოკურეშის", "საირმის", "სპათაგორის", "ტვიშის", "უსახელოს", "ქვედა ცაგერის",
            "ღვირიშის", "ჩქუმის", "ჩხუთელის", "წიფერჩის", "აბაშა", "გეზათის", "ზანათის", "კეთილარის", "მარნის",
            "ნაესაკაოს", "ნორიოს", "ონტოფო", "სამიქაოს", "სეფიეთი", "სუჯუნის", "ტყვირის", "ქოლობნის", "ძველი აბაშა",
            "წყემი", "აბასთუმნის", "ანაკლიის", "ახალი აბასთუმანი", "ახალკახათი", "განარჯიის მუხური", "გრიგოლიში",
            "დარჩელის", "დიდი ნეძი", "ერგეთის", "ინგირის", "კახათი", "კოკის", "კორცხელის", "ნარაზენის", "ორსანტია",
            "ორულის", "რიყე", "რუხის", "ურთა", "ყულიშკარი", "შამგონა", "ჩხორიის", "ცაიშის", "ჭაქვინჯის", "ჭითაწყარი",
            "ჭკადუაში", "ჯიხასკარის", "მარტვილი", "აბედათის", "ბანძის", "გაჭედილის", "გურძემის", "დიდი ჭყონის",
            "დოშაყის", "ვედიდკარის", "თამაკონის", "ინჩხურის", "კიციას", "კურზუს", "ლეხაინდრავოს", "ნაგვაზავოს",
            "ნახუნავოს", "ნაჯავახოს", "ონოღია", "სერგიეთის", "ტალერის", "ხუნწის", "მესტია", "ბეჩოს", "იდლიანის",
            "იფარის", "კალის", "ლატალის", "ლახამულას", "ლენჯერის", "მულახის", "ნაკრას", "უშგულის", "ფარის", "ცხუმარის",
            "წვირმის", "ჭუბერის", "ხაიშის", "სენაკი", "გეჯეთი", "ეკის", "ზანის", "ზემო ჭალადიდის", "თეკლათის",
            "ლეძაძამეს", "მენჯის", "ნოსირის", "ნოქალაქევის", "უშაფათის", "ფოცხოს", "ძველი სენაკი", "ხორშის",
            "ჩხოროწყუს", "ახუთის", "ზუმი", "თაია", "კირცხის", "ლესიჭინეს", "ლეწურწუმეს", "ნაკიანის", "ნაფიჩხოვოს",
            "ქვედა ჩხოროწყუს", "ჭოღას", "ხაბუმეს", "წალენჯიხის", "ჯვარის", "ლიის", "მედანის", "მიქავას", "მუჟავის",
            "ნაკიფუს", "ობუჯის", "ფახულანის", "ჩხვალერის", "ჭალეს", "ჯგალის", "ხობი", "ახალხიბულის", "ბიის",
            "გურიფულის", "ზემო ქვალონის", "ტორსა-ძღვაბას", "ნოჯიხევის", "პატარა ფოთი", "პირველი ხორგა", "საგვიჩიო",
            "საჯიჯაო", "ქარიათის", "ქვემო ქვალონის", "ყულევი", "შავღელე", "შუა ხორგის", "ძველი ხიბულა", "ჭალადიდის",
            "ხამისქური", "ხეთის", "ადიგენი", "აბასთუმანი", "არალის", "ბენარის", "ბოლაჯურის", "გორგულის", "დერცელის",
            "ვარხანის", "ზანავის", "ზარზმის", "ზედუბნის", "ლელოვნის", "მლაშის", "მოხეს", "უდის", "ფხეროს", "ჩორჩანის",
            "ცახანის", "ჭეჭლის", "ხევაშენის", "ასპინძა", "აწყვიტა", "დამალა", "ვარგავის", "იდუმალას", "ნაქალაქევის",
            "ოთა", "ორგორის", "რუსთავი", "ტოლოშის", "ძველის", "ხიზაბავრას", "ახალქალაქი", "აზავრეთის", "ალასტანის",
            "არაგვას", "ბარალეთის", "გოგაშენის", "დილისკა", "ვაჩიანის", "ზაკვის", "კარტიკამის", "კარწახის", "კოთელია",
            "კოჭიოს", "კუმურდოს", "ოკამის", "პტენა", "სულდის", "ტურცხის", "ჩუნჩხა", "ხავეთის", "ხანდო", "ხოსპიოს",
            "ვალე", "ანდრიაწმინდის", "აწყურის", "ელიაწმინდის", "კლდის", "მინაძის", "პამაჯის", "საძელის", "სვირის",
            "სხვილისის", "ურავლის", "ფერსის", "წყალთბილას", "წყრუთის", "ბორჯომი", "ახალდაბა", "ბაკურიანის", "წაღვერის",
            "ბალანთის", "გვერდისუბნის", "გუჯარეთის", "დვირის", "ტაბაწყურის", "ტაძრისის", "ტბის", "ყვიბისის",
            "ციხისჯვარი", "განძის", "გორელოვკის", "გონდრიოს", "ეშთიის", "სათხის", "ტამბოვკის", "ფოკის", "ხანჩალის",
            "ჯიგრაშენი", "ბოლნისი", "თამარისის", "კაზრეთის", "აკაურთას", "დარბაზის", "დისველი", "მამხუთის", "ნახიდურის",
            "რატევანი", "ტაჭისუბანი", "ტალავერი", "ტანძია", "ქვემო ბოლნისი", "ქვეში", "გარდაბანი", "აღთაკლია",
            "ახალი სამგორი", "ახალსოფელი", "ვახტანგისი", "თელეთი", "კალინინო", "კრწანისი", "კუმისი", "ლემშვენიერა",
            "მარტყოფი", "ნაზარლო", "სართიჭალის", "ქესალო", "ყარათაკლია", "ყარაჯალარი", "ჯანდარა", "დმანისი", "ამამლოს",
            "განთიადის", "გომარეთის", "გუგუთის", "დიდი დმანისის", "დმანისის", "ირგანჩაი", "იფნარი", "კამარლოს",
            "მაშავერა", "ოროზმანის", "საკირეს", "სარკინეთის", "ყარაბულახი", "ყიზილკილისას", "თეთრიწყაროს", "მანგლისის",
            "ასურეთის", "ბორბალოს", "გოლთეთის", "დაღეთის", "დურნუკის", "თონეთის", "ირაგის", "კლდეისის", "კოდის",
            "მარაბდის", "ორბეთის", "შეკვეთილის", "ჩხიკვთის", "წინწყაროს", "ჭივჭავის", "ჯორჯიაშვილის", "მარნეული",
            "ალგეთის", "ახკერპის", "დამია-გეურარხის", "კასუმლოს", "კაპანახჩის", "კაჩაგანის", "ოფრეთის", "სადახლოს",
            "ქუთლიარის", "ყიზილ-აჯლო", "ყულარის", "შაუმიანის", "შულავერის", "წერაქვის", "წერეთელის", "ხოჯორნის",
            "წალკის", "ბედიანის", "თრიალეთის", "ავრანლო", "აიაზმი", "არწივანი", "არჯევან-სარვანის", "აშკალა", "ბარეთის",
            "ბერთა", "ბეშთაშენის", "ბურნაშეთი", "გუმბათი", "დარაკოვი", "დაშბაში", "თეჯისი", "კაბური", "ყიზილ-კილისა",
            "კოხტას", "კუში", "ნარდევანი", "ოზნი", "რეხა", "სამება", "საყდრიონი", "ჩივთ-კილისა", "ხანდოს", "ხაჩკოვი",
            "ატენის", "ახალუბნის", "ბერბუკის", "ბოშურის", "დიცის", "ვარიანის", "ზეღდულეთის", "კარალეთის", "მერეთის",
            "მეჯვრისხევის", "მღებრიანის", "ნიქოზის", "საყავრის", "სკრის", "ტინისხიდის", "ტირძნისის", "ტყვიავის",
            "ქვახვრელის", "შავშვების", "შინდისის", "ძევერის", "ხიდისთავი", "კასპი", "აღაიანის", "ახალქალაქის",
            "ახალციხის", "დოესის", "ზემო ხანდაკის", "თელიანის", "კავთისხევის", "კოდისწყაროს", "ლამისყანის", "მეტეხის",
            "სამთავისის", "ქვემო გომის", "ქვემო ჭალის", "წინარეხის", "ხოვლე", "ქარელი", "აბისის", "ავლევის", "ბებნისის",
            "ბრეთის", "ბრეძის", "გიგანტის", "დვანის", "დირბი", "ზემო ხვედურეთი", "ზღუდრის", "კეხიჯვრის", "მოხისის",
            "რუისი", "ურბნისი", "ფცის", "ხაშურის", "სურამის", "ალის", "ოსიაურის", "ფლევის", "ქვიშხეთის", "ცოცხნარის",
            "ცხრამუხის", "წაღვლის", "წრომის", "ხალების", "ხცისის", "არცევის", "ბელოთი", "ბერულის", "ბიყარის", "გერის",
            "ვანათის", "ქსუისის", "ღრომის", "ყორნისი", "ავნევის", "ბალთის", "თერეგვანის", "თიღვის", "ყორნისის",
            "ძვილეთის", "ქემერტის", "ქურთის", "ძარის", "ხეთაგუროვოს", "ჯავა", "კვაისის", "ედისის", "ვანელის",
            "მსხლების", "როკის", "ქეშელთის", "ყემულთის", "წონის", "ხვწის", "ლენტეხი", "თიანეთი", "სტეფანწმინდა",
            "ბახმარო", "გუდაუთა", "ქუთაისი", "მცხეთა", "ხულო", "ხაშური", "ამბროლაური", "თელავი", "ზუგდიდი", "ფოთი",
            "ჟინვალი", "სურამი", "გორი", "ჯვარი", "ჩხოროწყუ", "ბაკურიანი", "ჩაქვი", "წაღვერი", "სიონი", "ბედიანი",
            "თრიალეთი", "ფასანაური", "თბილისი", "ბათუმი", "სოხუმი", "ცხინვალი", "მანგლისი", "ტყვარჩელი", "აგარა",
            "ხრამჰესი", "კაზრეთი", "წალკა", "წალენჯიხა", "თამარისი", "ახალციხე", "ოჩამჩირე", "თეთრიწყარო");

    public GeorgianToponyms() {
        super(tr("Georgian Toponym Validator"), tr("Checks whether city/town name is an official Georgian name."));
    }

    @Override
    public void visit(Node node) {
        if ((node.hasTag("place", "city") || node.hasTag("place", "town") || node.hasTag("place", "village")
                || node.hasTag("place", "neighbourhood") || node.hasTag("place", "quarter")) && isNodeInGeorgia(node)) {
            String nameTagValueKa = node.get("name:ka");
            String nameTagValueEn = node.get("name:en");

            if (nameTagValueKa != null && !isValidGeorgianNameKa(nameTagValueKa)) {
                errors.add(TestError.builder(this, Severity.WARNING, WRONG_NAME_KA)
                        .message(tr("Invalid Georgian name"),
                                marktr("Name:ka " + nameTagValueKa
                                        + " does not seem to be an official Georgian city/town name."),
                                node)
                        .primitives(node)
                        .build());
            }
            if (nameTagValueEn != null && !isValidGeorgianNameEn(nameTagValueEn)) {
                errors.add(TestError.builder(this, Severity.WARNING, WRONG_NAME_EN)
                        .message(tr("Invalid Georgian name"),
                                marktr("Name:en " + nameTagValueEn
                                        + " does not seem to be an official Georgian city/town name."),
                                node)
                        .primitives(node)
                        .build());
            }
        }
    }

    private boolean isValidGeorgianNameKa(String name) {
        return officialGeorgianTownNamesKa.contains(name);
    }

    private boolean isValidGeorgianNameEn(String name) {
        return officialGeorgianTownNamesEn.contains(name);
    }

    private boolean isNodeInGeorgia(Node node) {
        return checkReferrersRecursively(node, new HashSet<>());
    }

    private boolean checkReferrersRecursively(OsmPrimitive current, Set<Long> visited) {
        if (visited.contains(current.getUniqueId())) {
            return false;
        }
        visited.add(current.getUniqueId());

        if (current instanceof Relation) {
            Relation relation = (Relation) current;
            if ("2".equals(relation.get("admin_level")) && "Georgia".equals(relation.get("int_name"))) {
                return true;
            }
        }

        List<Relation> referrers = current.referrers(Relation.class).collect(Collectors.toList());
        for (Relation referrer : referrers) {
            if (checkReferrersRecursively(referrer, visited)) {
                return true;
            }
        }

        return false;
    }
}