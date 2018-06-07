public void sort(List<Integer> list, int insert) {
        if (list.size() == 0) {
            list.add(insert);
        } else {
            for (int i = list.size() - 1; i >= 0; i--) {
                if (i == list.size() - 1) {
                    if (list.get(i) > insert) {
                        list.add(list.get(i));
                        list.set(i, insert);
                    } else {
                        list.add(insert);
                        break;
                    }
                } else {
                    if (list.get(i) > insert) {
                        list.set(i+1,list.get(i));
                        list.set(i, insert);
                    }
                }
            }
        }
    }
