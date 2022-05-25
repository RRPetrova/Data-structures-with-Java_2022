import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OlympicsImpl implements Olympics {


    private HashSet<Competitor> competitorList;
    private List<Competition> competitionList;

    public OlympicsImpl() {
        this.competitorList = new HashSet<>();
        this.competitionList = new ArrayList<>();
    }


    @Override
    public void addCompetitor(int id, String name) {
        Competitor current = searchCompetitorById(id);
        if (current != null) {
            throw new IllegalArgumentException("Already exists");
        }
        Competitor competitor = new Competitor(id, name);
        this.competitorList.add(competitor);
    }

    @Override
    public void addCompetition(int id, String name, int score) {
        Competition current = searchCompetitionById(id);
        if (current != null) {
            throw new IllegalArgumentException("Already exists");
        }
        Competition competition = new Competition(name, id, score);
        this.competitionList.add(competition);
    }

    @Override
    public void compete(int competitorId, int competitionId) {
        Competition currCompetition = searchCompetitionById(competitionId);
        Competitor currCompetitor = searchCompetitorById(competitorId);
        if (currCompetitor == null || currCompetition == null) {
            throw new IllegalArgumentException("Not found comp");
        }
        //TODO: if already exists case ?
        Collection<Competitor> currCompetitionCompetitors = currCompetition.getCompetitors();
        currCompetitor.setTotalScore(currCompetition.getScore() + currCompetitor.getTotalScore());
        currCompetitionCompetitors.add(currCompetitor);
        currCompetition.setCompetitors(currCompetitionCompetitors);
        this.competitionList.add(currCompetition);
    }

    @Override
    public void disqualify(int competitionId, int competitorId) {
        Competition currCompetition = searchCompetitionById(competitionId);
        Competitor currCompetitor = searchCompetitorById(competitorId);
        if (currCompetitor == null || currCompetition == null) {
            throw new IllegalArgumentException("Not found");
        }

        Competitor competitorInThisCompetition = null;
//                = currCompetition
//                .getCompetitors()
//                .stream()
//                .filter(c -> c.getId() == currCompetitor.getId())
//                .findFirst()
//                .orElse(null);


        for (Competitor competitor : currCompetition.getCompetitors()) {
            if (competitor.getId() == currCompetitor.getId()) {
                competitorInThisCompetition = competitor;
                currCompetition.getCompetitors().remove(competitorInThisCompetition);
                //  long score= currCompetitor.getTotalScore();
                //  int score1 = currCompetition.getScore();
                currCompetitor.setTotalScore(currCompetitor.getTotalScore() - currCompetition.getScore());
                break;
            }
        }

        if (competitorInThisCompetition == null) {
            throw new IllegalArgumentException("Not found");
        }
    }

    @Override
    public Iterable<Competitor> findCompetitorsInRange(long min, long max) {
//        Set<Competitor> collect = this.competitorList
//                .stream()
//                .filter(c -> c.getTotalScore() > min && c.getTotalScore() <= max)
//                .collect(Collectors.toSet());
    List<Competitor> res = new ArrayList<>();
        for (Competitor competitor : this.competitorList) {
            if (competitor.getTotalScore() > min && competitor.getTotalScore() <= max) {
                res.add(competitor);
            }
        }
        res.sort(Comparator.comparingInt(Competitor::getId));

        return res;
    }

    @Override
    public Iterable<Competitor> getByName(String name) {

        List<Competitor> collect = this.competitorList
                .stream()
                .filter(c -> c.getName().equals(name))
                .sorted(Comparator.comparingInt(Competitor::getId))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new IllegalArgumentException("Not found");
        }
        return collect;
    }

    @Override
    public Iterable<Competitor> searchWithNameLength(int minLength, int maxLength) {
        List<Competitor> res = new ArrayList<>();
        for (Competitor currCompetitor : this.competitorList) {
            if (currCompetitor.getName().length() >= minLength && currCompetitor.getName().length() <= maxLength) {
                res.add(currCompetitor);
            }
        }

        res.sort(Comparator.comparingInt(Competitor::getId));
        return res;
//        return this.competitorList
//                .stream()
//                .filter(c -> c.getName().length() >= minLength && c.getName().length() <= maxLength)
//                .sorted(Comparator.comparingInt(Competitor::getId))
//                .collect(Collectors.toList());
    }

    @Override
    public Boolean contains(int competitionId, Competitor comp) {
        Competition compn = searchCompetitionById(competitionId);

        if (compn == null) {
            throw new IllegalArgumentException("Not found with cmpn id: " + competitionId);
        }
        return compn.getCompetitors()
                .stream()
                .anyMatch(competitor -> competitor.getId() == comp.getId());
    }

    @Override
    public int competitionsCount() {
        return this.competitionList.size();
    }

    @Override
    public int competitorsCount() {
        return this.competitorList.size();
    }

    @Override
    public Competition getCompetition(int id) {
        Competition current = searchCompetitionById(id);
        if (current == null) {
            throw new IllegalArgumentException("Not valid competition with id " + id);
        }
        return current;
    }

    private Competitor searchCompetitorById(int id) {
//        return competitorList
//                .stream()
//                .filter(c -> c.getId() == id)
//                .findFirst()
//                .orElse(null);

        for (Competitor currCompetitor : this.competitorList) {
            if (currCompetitor.getId() == id) {
                return currCompetitor;
            }
        }
        return null;

    }

    private Competition searchCompetitionById(int id) {
//        return competitionList
//                .stream()
//                .filter(c -> c.getId() == id)
//                .findFirst()
//                .orElse(null);

        for (Competition currCompetition : this.competitionList) {
            if (currCompetition.getId() == id) {
                return currCompetition;
            }

        }
        return null;
    }
}
