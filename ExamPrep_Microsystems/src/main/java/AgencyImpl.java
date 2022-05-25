import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AgencyImpl implements Agency {

    private Map<String, Invoice> invoicesBySN;
    private Map<LocalDate, Set<Invoice>> invoicesByDueDate;
    private List<Invoice> paidInvoices;

    public AgencyImpl() {
        this.invoicesBySN = new HashMap<>();
        this.invoicesByDueDate = new TreeMap<>();
       this.paidInvoices = new ArrayList<>();
    }

    @Override
    public void create(Invoice invoice) {
        if (contains(invoice.getNumber())) {
            throw new IllegalArgumentException();
        }
        this.invoicesBySN.put(invoice.getNumber(), invoice);

       if (!this.invoicesByDueDate.containsKey(invoice.getDueDate())) {
           this.invoicesByDueDate.put(invoice.getDueDate(), new HashSet<>());
        }

        this.invoicesByDueDate.get(invoice.getDueDate()).add(invoice);
        if (invoice.getSubtotal() == 0) {
            this.paidInvoices.add(invoice);
        }
    }

    @Override
    public boolean contains(String number) {
        return this.invoicesBySN.containsKey(number);
    }

    @Override
    public int count() {
        return this.invoicesBySN.size();
    }

    @Override
    public void payInvoice(LocalDate dueDate) {
        Set<Invoice> collect = this.invoicesByDueDate.get(dueDate);
//TODO: null not empty
        if (collect == null) {
            throw new IllegalArgumentException();
        }
        collect.forEach(i -> {
            i.setSubtotal(0);
              this.paidInvoices.add(i);
        });

    }

    @Override
    public void throwInvoice(String number) {
        Invoice remove = this.invoicesBySN.get(number);
        if (remove == null) {
            throw new IllegalArgumentException();
        }
        this.invoicesBySN.remove(number);
        this.invoicesByDueDate.get(remove.getDueDate()).remove(remove);

        if (remove.getSubtotal() == 0) {
            this.paidInvoices.remove(remove);
        }
    }

    @Override
    public void throwPayed() {
        this.paidInvoices.forEach(i -> {
            this.invoicesByDueDate.get(i.getDueDate()).remove(i);
            this.invoicesBySN.remove(i.getNumber());
        });

        this.paidInvoices = new ArrayList<>();

//        Map<String, Invoice> newBySn = new HashMap<>();
//
//        for (Map.Entry<String, Invoice> entry : this.invoicesBySN.entrySet()) {
//
//            if (entry.getValue().getSubtotal() > 0) {
//                newBySn.put(entry.getKey(), entry.getValue());
//            } else {
//                this.invoicesByDueDate.get(entry.getValue().getDueDate()).remove(entry.getValue());
//            }
//
//        }
//        this.invoicesBySN = newBySn;

    }

    @Override
    public Iterable<Invoice> getAllInvoiceInPeriod(LocalDate startDate, LocalDate endDate) {
        return this.invoicesBySN
                .values()
                .stream()
                .filter(a -> a.getIssueDate().compareTo(startDate) >= 0
                        && a.getIssueDate().compareTo(endDate) <= 0)
                .sorted(Comparator.comparing(Invoice::getIssueDate)
                        .thenComparing(Invoice::getDueDate))
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<Invoice> searchByNumber(String number) {
        List<Invoice> collect = this.invoicesBySN
                .values()
                .stream()
                .filter(a -> a.getNumber().contains(number))
                .collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return collect;
    }

    @Override
    public Iterable<Invoice> throwInvoiceInPeriod(LocalDate startDate, LocalDate endDate) {

        List<Invoice> inPeriod = this.invoicesBySN
                .values()
                .stream()
                .filter(i -> i.getDueDate().compareTo(startDate) > 0
                        && i.getDueDate().compareTo(endDate) < 0)
                .sorted(Comparator.comparing(Invoice::getIssueDate)
                        .thenComparing(Invoice::getDueDate))
                .collect(Collectors.toList());
//        Map<LocalDate, List<Invoice>> res = new HashMap<>();
//        List<Invoice> inPeriod = new ArrayList<>();
//
//        for (LocalDate localDate : this.invoicesByDueDate.keySet()) {
//            if (localDate.isAfter(startDate) && localDate.isBefore(endDate)) {
//                List<Invoice> toRemove = this.invoicesByDueDate.get(localDate);
//                toRemove.forEach(a -> this.invoicesBySN.remove(a.getNumber()));
//                inPeriod.addAll(toRemove);
//            } else {
//                res.put(localDate, this.invoicesByDueDate.get(localDate));
//            }
//        }
//        this.invoicesByDueDate = res;

        if (inPeriod.isEmpty()) {
            throw new IllegalArgumentException();
        }

        inPeriod.forEach(i -> {
            this.invoicesBySN.remove(i.getNumber());
            this.invoicesByDueDate.get(i.getDueDate()).remove(i);

            if (i.getSubtotal() == 0) {
                this.paidInvoices.remove(i);
            }
        });
        return inPeriod;
    }

    @Override
    public Iterable<Invoice> getAllFromDepartment(Department department) {
        return this.invoicesBySN
                .values()
                .stream()
                .filter(a -> a.getDepartment() == department)
               .sorted(Comparator.comparingDouble(Invoice::getSubtotal).reversed()
                        .thenComparing(Invoice::getIssueDate))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Invoice> getAllByCompany(String companyName) {
        return this.invoicesBySN
                .values()
                .stream()
                .filter(a -> a.getCompanyName().equals(companyName))
                .sorted(Comparator.comparing(Invoice::getNumber).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void extendDeadline(LocalDate endDate, int days) {

        Set<Invoice> invoices = this.invoicesByDueDate.get(endDate);
        if (invoices== null) {
            throw new IllegalArgumentException();
        }
        LocalDate localDate = endDate.plusDays(days);
        invoices.forEach(a -> a.setDueDate(localDate));

//        this.invoicesByDueDate.remove(endDate);
//        List<Invoice> extended = this.invoicesByDueDate.get(localDate);
//
//        if (extended == null) {
//            extended = new ArrayList<>();
//        }
//        extended.addAll(invoices);
//        this.invoicesByDueDate.put(localDate, extended);

    }
}
