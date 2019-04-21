package com.example.XML;

import com.example.humans.HumansRepository;

public interface XML {
    String toExport(HumansRepository humansRepository);
    HumansRepository toImport(String xmlData);
}
