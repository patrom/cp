package cp.out.orchestration.orchestra;

import cp.out.instrument.brass.*;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.percussion.determinate.Timpani;
import cp.out.instrument.percussion.determinate.Xylophone;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;

public class ViennaOrchestra extends Orchestra {

    public ViennaOrchestra() {
        piccolo = new InstrumentMapping(new Piccolo(), 2, 1);
        flute = new InstrumentMapping(new Flute(), 2, 2);
        flute2 = new InstrumentMapping(new Flute(), 2, 39);
        altoFlute = new InstrumentMapping(new AltoFlute(), 2, 34);
        oboe = new InstrumentMapping(new Oboe(), 2, 3);
        oboe2 = new InstrumentMapping(new Oboe(), 2, 40);
        corAnglais = new InstrumentMapping(new CorAnglais(), 2, 4);
        clarinetEflat = new InstrumentMapping(new ClarinetEFlat(), 2, 5);
        clarinet = new InstrumentMapping(new Clarinet(), 2, 6);
        clarinet2 = new InstrumentMapping(new Clarinet(), 2, 41);
        bassClarinet = new InstrumentMapping(new BassClarinet(), 2, 33);
        bassoon = new InstrumentMapping(new Bassoon(), 2, 7);
        bassoon2 = new InstrumentMapping(new Bassoon(), 2, 42);
        contrabassoon = new InstrumentMapping(new ContraBassoon(), 2, 8);
        map.put(piccolo, new ArrayList<>());
        map.put(flute, new ArrayList<>());
        map.put(flute2, new ArrayList<>());
        map.put(altoFlute, new ArrayList<>());
        map.put(oboe, new ArrayList<>());
        map.put(oboe2, new ArrayList<>());
        map.put(corAnglais, new ArrayList<>());
        map.put(clarinetEflat, new ArrayList<>());
        map.put(clarinet, new ArrayList<>());
        map.put(clarinet2, new ArrayList<>());
        map.put(bassClarinet, new ArrayList<>());
        map.put(bassoon, new ArrayList<>());
        map.put(bassoon2, new ArrayList<>());
        map.put(contrabassoon, new ArrayList<>());

        horn = new InstrumentMapping(new FrenchHorn(), 2, 9);
        horn2 = new InstrumentMapping(new FrenchHorn(), 2, 10);
        trumpet = new InstrumentMapping(new Trumpet(), 2, 12);
        trumpet2 = new InstrumentMapping(new Trumpet(), 2, 13);
        trombone = new InstrumentMapping(new Trombone(), 2, 14);
        trombone2 = new InstrumentMapping(new Trombone(), 2, 44);
        bassTrombone = new InstrumentMapping(new BassTrombone(), 2, 15);
        tuba = new InstrumentMapping(new Tuba(), 2, 16);
        map.put(horn, new ArrayList<>());
        map.put(horn2, new ArrayList<>());
        map.put(trumpet, new ArrayList<>());
        map.put(trumpet2, new ArrayList<>());
        map.put(trombone, new ArrayList<>());
        map.put(trombone2, new ArrayList<>());
        map.put(bassTrombone, new ArrayList<>());
        map.put(tuba, new ArrayList<>());

        timpani =  new InstrumentMapping(new Timpani(), 2, 17);

        glockenspiel = new InstrumentMapping(new Glockenspiel(), 2, 22);
        celesta =  new InstrumentMapping(new Celesta(), 2, 24);
        xylophone =  new InstrumentMapping(new Xylophone(), 2, 23);
        piano =  new InstrumentMapping(new Piano(), 2, 25);
        harp =  new InstrumentMapping(new Harp(), 2, 26);

        map.put(glockenspiel, new ArrayList<>());
        map.put(celesta, new ArrayList<>());
        map.put(xylophone, new ArrayList<>());
        map.put(piano, new ArrayList<>());
        map.put(harp, new ArrayList<>());
        map.put(timpani, new ArrayList<>());

        violin1 = new InstrumentMapping(new ViolinsI(), 2, 28);
        violin2 = new InstrumentMapping(new ViolinsII(), 2, 29);
        viola = new InstrumentMapping(new Viola(), 2, 30);
        cello = new InstrumentMapping(new Cello(), 2, 31);
        bass = new InstrumentMapping(new DoubleBass(), 2, 32);
        map.put(violin1, new ArrayList<>());
        map.put(violin2, new ArrayList<>());
        map.put(viola, new ArrayList<>());
        map.put(cello, new ArrayList<>());
        map.put(bass, new ArrayList<>());
    }

}
